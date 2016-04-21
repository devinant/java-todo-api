package ac.opij.todo.core;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "todos")
@NamedQueries({
    @NamedQuery(name  = "ac.opij.todo.core.Todo.findAll", query = "SELECT t FROM Todo t")
})
public class Todo {
    private enum Status {
        INCOMPLETE, COMPLETE
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(required = true)
    private String description;

    @Column
    @ApiModelProperty(required = true)
    private Boolean status;

    public Todo() {}

    public Todo(String description, Boolean status) {
        this.description = description;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Todo todo = (Todo) o;

        return  Objects.equals(this.id, todo.id) &&
                Objects.equals(this.description, todo.description) &&
                Objects.equals(this.status, todo.status);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }
}
