package join.ag.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ToDoItemUpdateRequest extends ToDoItemAddRequest implements Serializable {

    @JsonProperty("isCompleted")
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ToDoItemUpdateRequest{" +
                "text='" + getText() + '\'' +
                "completed='" + completed + '\'' +
                '}';
    }
}
