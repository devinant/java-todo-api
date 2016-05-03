package ac.opij.todo.resources;

import ac.opij.todo.core.Todo;
import ac.opij.todo.db.TodoDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoResourceTest {
    private static final TodoDAO DAO = mock(TodoDAO.class);

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new TodoResource(DAO))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();

    private Todo todo;

    private WebTarget todoTarget(String param) {
        return RULE.getJerseyTest().target(String.format("/todo/%s", param));
    }

    @Before
    public void setUp() throws Exception {
        this.todo = new Todo();
        this.todo.setId(1L);
    }

    @After
    public void tearDown() throws Exception {
        reset(DAO);
    }

    @Test
    public void testTodoWasFound() throws Exception {
        when(DAO.findById(1L)).thenReturn(Optional.of(this.todo));

        Todo todo = todoTarget("1")
                .request()
                .get(Todo.class);

        assertThat(todo.getId()).isEqualTo(this.todo.getId());
        verify(DAO).findById(1L);
    }

    @Test
    public void testTodoNotFound() throws Exception {
        when(DAO.findById(2L)).thenReturn(Optional.empty());

        final Response response = todoTarget("2")
                .request()
                .get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }
}
