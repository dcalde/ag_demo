package join.ag.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class BalanceTestResult implements Serializable{

    private String input;

    @JsonProperty(value = "isBalanced")
    private boolean balanced;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    @Override
    public String toString() {
        return "BalanceTestResult{" +
                "input='" + input + '\'' +
                ", balanced=" + balanced +
                '}';
    }
}
