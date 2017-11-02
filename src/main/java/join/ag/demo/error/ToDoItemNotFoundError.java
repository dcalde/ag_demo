package join.ag.demo.error;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ToDoItemNotFoundError implements Serializable {

    private List<Message> details;
    private String name;

    public ToDoItemNotFoundError(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getDetails() {
        return details;
    }

    public void setDetails(List<Message> details) {
        this.details = details;
    }

    public ToDoItemNotFoundError addDetail(String message) {
        if(details == null) {
            details = new ArrayList<>(1);
        }
        details.add(new Message(message));
        return this;
    }
}
