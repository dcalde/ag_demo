package join.ag.demo;

import join.ag.demo.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

	@Autowired
	private TaskService taskService;

	@Test
	public void testCheckIfBracketsBalanced() {

		assertThat(taskService.checkIfBracketsBalanced("({[]})")).isTrue();
		assertThat(taskService.checkIfBracketsBalanced("")).isTrue();
		assertThat(taskService.checkIfBracketsBalanced("{")).isFalse();
		assertThat(taskService.checkIfBracketsBalanced("}")).isFalse();
		assertThat(taskService.checkIfBracketsBalanced("{[}]")).isFalse();
		assertThat(taskService.checkIfBracketsBalanced("[{)]")).isFalse();

	}

}
