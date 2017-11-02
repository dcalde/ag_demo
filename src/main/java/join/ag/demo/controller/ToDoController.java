package join.ag.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import join.ag.demo.dto.ToDoItemAddRequest;
import join.ag.demo.dto.ToDoItemUpdateRequest;
import join.ag.demo.error.EntityNotFoundException;
import join.ag.demo.error.ErrorDetail;
import join.ag.demo.error.ToDoItemNotFoundError;
import join.ag.demo.error.ToDoItemValidationError;
import join.ag.demo.model.ToDoItem;
import join.ag.demo.service.ToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import static java.util.stream.Collectors.toList;

@RestController
@Api(tags = "todo", description = "To Do List endpoints")
@RequestMapping("/todo")
@Validated
public class ToDoController {

    private static final Logger log = LoggerFactory.getLogger(ToDoController.class);

    @Autowired
    private ToDoService toDoService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ToDoItemValidationError handleMethodArgumentTypeMismatch(MethodArgumentNotValidException ex, WebRequest request) {
        ToDoItemValidationError validationError = new ToDoItemValidationError("ValidationError");
        validationError.setDetails(ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new ErrorDetail("params", e))
                .collect(toList()));
        return validationError;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ToDoItemNotFoundError handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        log.warn("ToDo Item {} not found", request.getContextPath());
        return new ToDoItemNotFoundError("NotFoundError")
                .addDetail(ex.getMessage());
    }


    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a to do item")
    @ApiResponses({
            @ApiResponse(code = 200, response = ToDoItem.class, message = "OK"),
            @ApiResponse(code = 400, response = ToDoItemValidationError.class, message = "Validation error")})
    public ToDoItem createItem(@Valid @RequestBody  ToDoItemAddRequest newToDoItem) {
        log.debug("Saving new ToDo : {}", newToDoItem.getText());
        ToDoItem item = toDoService.save(newToDoItem);
        log.info("Saved new ToDo : {}", item);
        return item;
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve a specific item by id")
    @ApiResponses({
            @ApiResponse(code = 200, response = ToDoItem.class, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found Error")})
    public ResponseEntity<ToDoItem> retrieveItemById(@PathVariable("id") long id) {
        return toDoService.loadById(id)
            .map(result -> ResponseEntity.ok(result))
            .orElse(ResponseEntity.notFound()
                    .header("X-ERROR-MSG", "Item not found for id : " + id)
                    .build());
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify an item")
    @ApiResponses({
            @ApiResponse(code = 200, response = ToDoItem.class, message = "OK"),
            @ApiResponse(code = 400, response = ToDoItemValidationError.class, message = "Validation error"),
            @ApiResponse(code = 404, response = ToDoItemNotFoundError.class, message = "Not Found Error")})
    public ResponseEntity<ToDoItem> updateItem(@PathVariable("id") Long id, @Valid @RequestBody ToDoItemUpdateRequest newToDoItem) {
        return toDoService.update(id, newToDoItem)
                .map(result -> ResponseEntity.ok(result))
                .orElseThrow(() -> new EntityNotFoundException("Invalid id : "+id));
    }

}