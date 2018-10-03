package org.wicaksono.rest.nosql.dao;

import org.wicaksono.rest.nosql.model.Employee;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Inject;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
public class TestDAO {

    private final ConnectionManager connectionManager;

    @Inject
    public TestDAO(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    public String getTest(){
        final Employee elmer = new Employee();
        elmer.setName("Elmer Fudd");
        elmer.setSalary(50000.0);
        connectionManager.getDatastore().save(elmer);

        return "get Todo Logic Test - DAO - Inject";
    }
}
