package join.ag.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "todo")
@SequenceGenerator(name="todo_seq", sequenceName = "ag.s_todo", allocationSize = 1)
public class ToDoItem implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="todo_seq")
    @ApiModelProperty(example = "42")
    private long id;

    @Column(name = "text", nullable = false, length = 50)
    @ApiModelProperty(example = "Uulwi ifis halahs gag erh'ongg w'ssh.")
    private String text;

    @Column(name = "completed", nullable = false)
    @ApiModelProperty(example = "false")
    private boolean isCompleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    @ApiModelProperty(example = "2017-10-13T01:50:58.735Z")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdAt;

    public ToDoItem() {
    }

    public ToDoItem(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDoItem toDoItem = (ToDoItem) o;

        if (isCompleted != toDoItem.isCompleted) return false;
        return text != null ? text.equals(toDoItem.text) : toDoItem.text == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (isCompleted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCompleted=" + isCompleted +
                ", createdAt=" + createdAt +
                '}';
    }
}
