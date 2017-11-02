package join.ag.demo;

import join.ag.demo.controller.ToDoController;
import join.ag.demo.dto.ToDoItemAddRequest;
import join.ag.demo.model.ToDoItem;
import join.ag.demo.service.ToDoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToDoService toDoService;

	@Before
	public void setup()
	{

		ToDoItemAddRequest addRequest = new ToDoItemAddRequest();
		addRequest.setText("Do Stuff");

		ToDoItem mockItem = new ToDoItem("Do Stuff");
		mockItem.setCreatedAt(Date.from(Instant.parse("2017-10-13T01:50:58.735Z")));
		mockItem.setCompleted(false);
		mockItem.setId(1L);

		when(toDoService.save(addRequest)).thenReturn(mockItem);

		when(toDoService.loadById(1L)).thenReturn(ofNullable(mockItem));

		when(toDoService.loadById(2L)).thenReturn(empty());
	}

	@Test
	public void testAddToDoItem() throws Exception {

		mockMvc.perform(post("/todo")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"text\":\"Do Stuff\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("2017-10-13T01:50:58.735Z")))
				.andExpect(content().string(containsString("\"id\":1")));

	}

	@Test
	public void testGetToDoItem() throws Exception {

		mockMvc.perform(get("/todo/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Do Stuff")));

		mockMvc.perform(get("/todo/2"))
				.andExpect(status().isNotFound());

	}

}
