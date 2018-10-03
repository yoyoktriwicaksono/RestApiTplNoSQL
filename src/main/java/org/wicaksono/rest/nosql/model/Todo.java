package org.wicaksono.rest.nosql.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

/**
 * @author cluttered.code@gmail.com
 */
@ApiModel(description = "a todo element")
public class Todo {

    @ApiModelProperty(dataType = "java.lang.String", value = "unique identifier", required = true, example = "89a1e095-1f42-4a1f-bde0-1824f3487538")
    private final UUID id;

    @ApiModelProperty(value = "description", required = true, example = "Clean bathrooms")
    private final String description;

    @ApiModelProperty(value = "completed status", required = true)
    private final boolean completed;

    @JsonCreator
    public Todo(@JsonProperty("id") final UUID id,
                @JsonProperty("description") final String description,
                @JsonProperty("completed") final boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
