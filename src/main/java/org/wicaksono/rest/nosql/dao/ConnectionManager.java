package org.wicaksono.rest.nosql.dao;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.wicaksono.rest.nosql.config.ConfigurationManager;
import org.wicaksono.rest.nosql.config.MongoConfigData;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
//@Singleton
public class ConnectionManager {
    private final Morphia morphia = new Morphia();
    private final Datastore datastore;

    public ConnectionManager(){
        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("org.wicaksono.rest.nosql.model");

        // create the Datastore connecting to the default port on the local host
        MongoConfigData mongoConfigData = ConfigurationManager.getInstance().getMongoConfigData();
        /*
        security:
            authorization: enabled

        We can use like below if authorization is not enable, just use host, port and database

        datastore = morphia.createDatastore(
                new MongoClient(
                        mongoConfigData.host(),
                        mongoConfigData.port()),
                mongoConfigData.database()
        );

         */

        ServerAddress addr = new ServerAddress(
                mongoConfigData.host(),
                mongoConfigData.port()
        );
        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        MongoCredential credentia = MongoCredential.createCredential(
                mongoConfigData.username(), mongoConfigData.database(), mongoConfigData.password().toCharArray());
        credentialsList.add(credentia);
        MongoClient client = new MongoClient(addr, credentialsList);

        datastore = morphia.createDatastore(
                client,
                mongoConfigData.database()
        );
        datastore.ensureIndexes();

    }

    public Datastore getDatastore(){
        return datastore;
    }
}