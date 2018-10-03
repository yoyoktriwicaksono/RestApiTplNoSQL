package org.wicaksono.rest.nosql.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.List;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
@Entity(value = "employees", noClassnameStored = true)
@Indexes(
        @Index(value = "salary", fields = @Field("salary"))
)
@Data
public class Employee {
    @Id
    private ObjectId id;
    private String name;
    @Reference
    private Employee manager;
    @Reference
    private List<Employee> directReports;
    @Property("wage")
    private Double salary;
}