package join.ag.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BalanceRequest {

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(notes = "Input string (max length 50)", required = true)
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "BalanceRequest{" +
                "input='" + input + '\'' +
                '}';
    }
}
