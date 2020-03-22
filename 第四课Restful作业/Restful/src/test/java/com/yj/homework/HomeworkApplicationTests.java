package com.yj.homework;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yj.homework.controller.TaskController;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkApplicationTests {

	private MockMvc mvc;

	@Autowired
	private TaskController taskController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(taskController).build();
	}

	@Test
	public void testTaskController() throws Exception {

		RequestBuilder request;

		//任务列表为空时，查询全部任务的返回
		request = get("/api/tasks/");
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"Task list is empty\",\"data\":null}")));
		
		//添加一个任务，且任务id不与列表中的已有任务重复
		request = post("/api/tasks/")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"id\":\"1\",\"content\":\"Restful API homework\",\"createdTime\":\"2019-05-15T00:00:00Z\"}");
			mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"success\",\"data\":null}")));

		//根据id查询任务，且列表中包含对应id的任务
		request = get("/api/tasks/1");
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"success\",\"data\":{\"id\":1,\"content\":\"Restful API homework\",\"createdTime\":\"2019-05-15T00:00:00Z\"}}")));

		//任务列表中只有一个任务时，查询全部任务的返回
		request = post("/api/tasks/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\",\"content\":\"example2\",\"createdTime\":\"2019-07-16T00:00:00Z\"}");
				mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"Task ID already exists\",\"data\":null}")));
				
		
		request = post("/api/tasks/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"2\",\"content\":\"example2\",\"createdTime\":\"2019-07-16T00:00:00Z\"}");
				mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"success\",\"data\":null}")));		

		//任务列表中有多个任务时，查询全部任务的返回
		request = get("/api/tasks/");
				mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"success\",\"data\":[{\"id\":1,\"content\":\"Restful API homework\",\"createdTime\":\"2019-05-15T00:00:00Z\"},{\"id\":2,\"content\":\"example2\",\"createdTime\":\"2019-07-16T00:00:00Z\"}]}")));
				
		//根据id删除任务，且列表中包含对应id的任务
		request = delete("/api/tasks/2");
				mvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"success\",\"data\":null}")));
			
		//根据id删除任务，但列表中不包含对应id的任务
		request = delete("/api/tasks/3");
				mvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"There is no task with this id\",\"data\":null}")));
						
		request = delete("/api/tasks/1");
				mvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"success\",\"data\":null}")));
							
		
		//删除任务时列表已经为空
		request = delete("/api/tasks/3");
				mvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().string(equalTo("{\"status\":200,\"message\":\"Task list is empty\",\"data\":null}")));
	}

}
