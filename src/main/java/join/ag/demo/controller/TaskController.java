package join.ag.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import join.ag.demo.dto.BalanceRequest;
import join.ag.demo.dto.BalanceTestResult;
import join.ag.demo.error.ErrorDetail;
import join.ag.demo.error.ToDoItemValidationError;
import join.ag.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import static java.util.stream.Collectors.toList;

@RestController
@Api(tags = "tasks", description = "General algorithmic tasks")
@Validated
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Void> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ConstraintViolation firstViolation = ex.getConstraintViolations().iterator().next();
        return ResponseEntity.badRequest()
                .header("X-ERROR-TARGET", firstViolation.getPropertyPath().toString())
                .header("X-ERROR-MSG", firstViolation.getMessage())
                .body(null);
    }

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
        boolean balanced = taskService.checkIfBracketsBalanced(request.getInput());
        BalanceTestResult result = new BalanceTestResult();
        result.setInput(request.getInput());
        result.setBalanced(balanced);
        return ResponseEntity.ok(result);
    }
}