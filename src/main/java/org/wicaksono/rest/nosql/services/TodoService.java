package org.wicaksono.rest.nosql.services;

import org.wicaksono.rest.nosql.logic.*;
import org.wicaksono.rest.nosql.model.Todo;
//import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cluttered.code@gmail.com
 */
@Singleton
@Api("Todo")
@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoService {

    private static final Map<UUID, Todo> data;

    private TestLogic testLogic;

    @Inject
    public TodoService(TestLogic testLogic){
        this.testLogic = testLogic;
    }


    static {
        final UUID id = UUID.fromString("89a1e095-1f42-4a1f-bde0-1824f3487538");
        data = new HashMap<>();
        data.put(id, new Todo(id, "First todo", false));
    }

    @GET
    @ApiOperation(value="List all todo items")
    public List<Todo> listAlarms() {
        return data.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @POST
    @ApiOperation(value="Create a todo item")
    public Todo createTodo(final Todo todo) {
        final UUID uuid = UUID.randomUUID();
        // final Todo newTodo = new Todo(uuid, todo.getDescription(), false);
        Todo newTodo = new Todo(uuid, testLogic.getTest(), false);
        data.put(uuid, newTodo);
        return newTodo;
    }

    @GET
    @Path("{id}")
    @ApiOperation(value="Fetch a todo item")
    public Todo getTodo(@PathParam("id") final UUID id) {
        final Todo todo = data.get(id);
        if(todo == null)
            throw new NotFoundException("Todo '" + id + "' not found");
        return todo;
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value="Update a todo item")
    public Todo updateTodo(@PathParam("id") final UUID id, final Todo todo) {
        if(data.get(id) == null)
            throw new NotFoundException("Todo '" + id + "' not found");
        final Todo updateTodo = new Todo(id, todo.getDescription(), todo.isCompleted());
        data.put(id, updateTodo);
        return updateTodo;
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value="Remove a todo item")
    public Void deleteTodo(@PathParam("id") final UUID id) {
        if(data.get(id) == null)
            throw new NotFoundException("Todo '" + id + "' not found");
        data.remove(id);
        return null;
    }
}