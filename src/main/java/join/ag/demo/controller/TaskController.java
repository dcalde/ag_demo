package join.ag.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import join.ag.demo.dto.BalanceRequest;
import join.ag.demo.dto.BalanceTestResult;
import join.ag.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "tasks", description = "General algorithmic tasks")
@Validated
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(
            value = "/validateBrackets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Checks if brackets in a string are balanced",
            notes = "Brackets in a string are considered to be balanced if the following criteria are met:\n" +
                    "* For every opening bracket (i.e., (, {, or [), there is a matching closing bracket (i.e., ), }, or ]) of" +
                    "the same type (i.e., ( matches ), { matches }, and [ matches ]). An opening bracket must appear before (to" +
                    "the left of) its matching closing bracket. For example, ]{}[ is not balanced.\n" +
                    "* No unmatched braces lie between some pair of matched bracket. For example, ({[]}) is balanced, but {[}]" +
                    "and [{)] are not balanced."

    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Error")})
    public ResponseEntity<BalanceTestResult> validateBrackets(@Valid @ModelAttribute BalanceRequest request, Errors errors) {
        if (errors.hasFieldErrors()) {
            return ResponseEntity.badRequest()
                    .header("X-ERROR-MSG", errors.getFieldError().getField() + " : "
                            + errors.getFieldError().getDefaultMessage()).body(null);
        }
        boolean balanced = taskService.checkIfBracketsBalanced(request.getInput());
        BalanceTestResult result = new BalanceTestResult();
        result.setInput(request.getInput());
        result.setBalanced(balanced);
        return ResponseEntity.ok(result);
    }
}