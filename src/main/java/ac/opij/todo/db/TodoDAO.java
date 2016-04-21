package ac.opij.todo.db;

import ac.opij.todo.core.Todo;
import java.util.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class TodoDAO extends AbstractDAO<Todo> {

    public TodoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(this.get(id));
    }

    public Todo create(Todo todo) {
        return this.persist(todo);
    }

    public List<Todo> findAll() {
        return this.list(this.namedQuery("ac.opij.todo.core.Todo.findAll"));
    }

    public void delete(Todo todo) {
        this.currentSession().delete(todo);
    }
}
