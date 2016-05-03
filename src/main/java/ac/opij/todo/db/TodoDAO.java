package ac.opij.todo.db;

import ac.opij.todo.core.Todo;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

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

    public Todo update(Todo todo) {
        return this.create(todo);
    }

    public List<Todo> findAll() {
        return this.list(this.namedQuery("ac.opij.todo.core.Todo.findAll"));
    }

    public void delete(Todo todo) {
        this.currentSession().delete(todo);
    }
}
