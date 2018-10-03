package org.wicaksono.rest.nosql.logic;

import org.wicaksono.rest.nosql.dao.TestDAO;
import org.wicaksono.rest.nosql.model.Employee;
import org.mongodb.morphia.Datastore;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class TestLogic {

    @Inject private TestDAO testDAO;

//    @Inject private Datastore datastore;
//    public TestLogic(){
//
//    }

    public String getTest(){

        return testDAO.getTest(); //"get Todo Logic Test - Inject";
    }
}