package org.wicaksono.rest.nosql.dao;

import org.wicaksono.rest.nosql.config.ConfigurationManager;
import org.wicaksono.rest.nosql.config.MongoConfigData;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;

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
        morphia.mapPackage("com.clutteredcode.rest.model");

        // create the Datastore connecting to the default port on the local host
        MongoConfigData mongoConfigData = ConfigurationManager.getInstance().getMongoConfigData();
        datastore = morphia.createDatastore(
                new MongoClient(
                        mongoConfigData.host(),
                        mongoConfigData.port()),
                mongoConfigData.database()
        );
        datastore.ensureIndexes();

    }

    public Datastore getDatastore(){
        return datastore;
    }
}