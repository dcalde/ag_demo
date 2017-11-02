package join.ag.demo.error;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ToDoItemValidationError implements Serializable{
    private String name;

    private List<ErrorDetail> details = new ArrayList<>();

    public ToDoItemValidationError(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ErrorDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ErrorDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ToDoItemValidationError{" +
                "name='" + name + '\'' +
                ", details=" + details +
                '}';
    }
}
