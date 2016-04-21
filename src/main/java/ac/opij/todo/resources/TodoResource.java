package ac.opij.todo.resources;

import ac.opij.todo.core.Todo;
import ac.opij.todo.db.TodoDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api("/todo")
@Path("/todo/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    private final TodoDAO todoDao;

    public TodoResource(TodoDAO todoDao) {
        this.todoDao = todoDao;
    }

    @GET
    @UnitOfWork
    public Todo get(@PathParam("id") LongParam todoId) {
        return this.todoDao.findById(todoId.get())
                .orElseThrow(() -> new NotFoundException("No such todo."));
    }

    @DELETE
    @UnitOfWork
    public void delete(@PathParam("id") LongParam todoId) {
        Todo todo = new Todo();
            todo.setId(todoId.get());

        this.todoDao.delete(todo);
    }
}
