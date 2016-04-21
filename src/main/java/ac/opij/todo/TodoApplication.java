package ac.opij.todo;

import ac.opij.todo.core.Todo;
import ac.opij.todo.db.TodoDAO;
import ac.opij.todo.resources.TodoResource;
import ac.opij.todo.resources.TodosResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class TodoApplication extends Application<TodoConfiguration> {
    private final HibernateBundle<TodoConfiguration> hibernateBundle = new HibernateBundle<TodoConfiguration>(Todo.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TodoConfiguration todoConfiguration) {
            return todoConfiguration.getDataSourceFactory();
        }
    };
    private final SwaggerBundle<TodoConfiguration> swaggerBundle = new SwaggerBundle<TodoConfiguration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(TodoConfiguration todoConfiguration) {
            return todoConfiguration.getSwaggerBundleConfiguration();
        }
    };

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.addBundle(this.swaggerBundle);
        bootstrap.addBundle(this.hibernateBundle);
    }

    @Override
    public void run(TodoConfiguration todoConfiguration, Environment environment) throws Exception {
        final TodoDAO dao = new TodoDAO(this.hibernateBundle.getSessionFactory());

        environment.jersey().register(new TodoResource(dao));
        environment.jersey().register(new TodosResource(dao));
    }

    public static void main(String[] args) {
        try {
            new TodoApplication().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
