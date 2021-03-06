package ac.opij.todo.resources;

import ac.opij.todo.core.Todo;
import ac.opij.todo.db.TodoDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Api("/todos")
@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodosResource {
    private final TodoDAO todoDao;

    public TodosResource(TodoDAO todoDao) {
        this.todoDao = todoDao;
    }

    @POST
    @UnitOfWork
    public Todo createTodo(Todo todo) {
        return todoDao.create(todo);
    }

    @PUT
    @UnitOfWork
    public Todo updateTodo(Todo todo) {
        return this.todoDao.update(todo);
    }

    @GET
    @UnitOfWork
    public List<Todo> listTodos() {
        return this.todoDao.findAll();
    }
}
