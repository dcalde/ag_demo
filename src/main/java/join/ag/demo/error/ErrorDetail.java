package join.ag.demo.error;

import org.springframework.validation.FieldError;

import java.io.Serializable;

public class ErrorDetail implements Serializable {
    private String location;
    private String param;
    private String msg;
    private String value;

    public ErrorDetail(String location, FieldError error) {
        this.location = location;
        this.value = String.valueOf(error.getRejectedValue());
        this.msg = error.getDefaultMessage();
        this.param = error.getField();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "location='" + location + '\'' +
                ", param='" + param + '\'' +
                ", msg='" + msg + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
