package ac.opij.todo;


import ac.opij.todo.core.Todo;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoIntegrationTest {
    private static final String CONFIG = "src/test/resources/server.yml";
    private Client client;

    @ClassRule
    public static final DropwizardAppRule<TodoConfiguration> RULE = new DropwizardAppRule<>(TodoApplication.class, CONFIG);

    private String createTarget(String endpoint) {
        return String.format("http://localhost:%d%s", RULE.getLocalPort(), endpoint);
    }

    @Before
    public void setUp() throws Exception {
        this.client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        this.client.close();
    }

    @Test
    public void testPostTodo() throws Exception {
        final Todo expected = new Todo("Buy Milk", false);
        final Todo actual   = this.client.target( this.createTarget("/todos") )
                .request()
                .post(Entity.entity(expected, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Todo.class);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
    }
}
