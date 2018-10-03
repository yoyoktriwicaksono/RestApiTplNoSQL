package org.wicaksono.rest.nosql.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
@ApiModel(description = "a Entity element")
@Data
@EqualsAndHashCode(callSuper = false)
public class EntityModel extends BaseEntity {
    @ApiModelProperty(value = "Name", required = false, example = "Test Name")
    private String Name;

    @ApiModelProperty(value = "Address", required = false, example = "Test Address")
    private String Address;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
