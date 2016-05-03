package ac.opij.todo.resources;

import ac.opij.todo.core.Todo;
import ac.opij.todo.db.TodoDAO;
import com.google.common.collect.ImmutableList;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodosResourceTest {
    private static final TodoDAO DAO = mock(TodoDAO.class);

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new TodosResource(DAO))
            .build();

    @Captor
    private ArgumentCaptor<Todo> todoCaptor;
    private Todo todo;

    private WebTarget todosTarget() {
        return RULE.client().target("/todos");
    }

    @Before
    public void setUp() throws Exception {
        this.todo = new Todo();
        this.todo.setDescription("Buy Milk");
        this.todo.setStatus(false);
    }

    @After
    public void tearDown() throws Exception {
        reset(DAO);
    }

    @Test
    public void testCreateTodo() throws Exception {
        when(DAO.create(any(Todo.class))).thenReturn(this.todo);

        final Response response = todosTarget()
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(this.todo, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(DAO).create(this.todoCaptor.capture());
        assertThat(this.todoCaptor.getValue()).isEqualTo(this.todo);
    }

    @Test
    public void testUpdateTodo() throws Exception {
        this.todo.setStatus(true);
        when(DAO.update(any(Todo.class))).thenReturn(this.todo);

        final Response response = todosTarget()
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(this.todo, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(DAO).update(this.todoCaptor.capture());
        assertThat(this.todoCaptor.getValue()).isEqualTo(this.todo);
    }

    @Test
    public void testListTodos() throws Exception {
        final ImmutableList<Todo> todos = ImmutableList.of(this.todo);

        when(DAO.findAll()).thenReturn(todos);

        final List<Todo> response = todosTarget()
                .request()
                .get(new GenericType<List<Todo>>() {});

        verify(DAO).findAll();
        assertThat(response).containsAll(todos);
    }
}
