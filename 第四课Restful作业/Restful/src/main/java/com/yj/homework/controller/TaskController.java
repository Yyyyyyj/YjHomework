package com.yj.homework.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yj.homework.model.Result;
import com.yj.homework.model.Task;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {

	//通过map模拟数据库中的task表
    static Map<Long, Task> tasks = Collections.synchronizedMap(new HashMap<Long, Task>());
    
	/**
	 * 查询所有task
	 * @return
	 */
	@GetMapping("/")
	public Result getAllTask() {
		
		List<Task> taskList = new ArrayList<>(tasks.values());
		if(taskList != null && taskList.size()>0) {
			return Result.build(200, "success", taskList);
		}else {
			return Result.build(200, "Task list is empty", null);
		}
    }

	/**
	 * 添加一个新任务
	 * @param task
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/",consumes = "application/json")
	public Result createTask(@RequestBody Task task) throws Exception {
		int size = tasks.size();
		Long id = task.getId();
		for(Task taskExist: tasks.values()) {
			if(id == taskExist.getId()) {
				return Result.build(200, "Task ID already exists", null);
			}
		}
		tasks.put((long) (size+1), task);
		return Result.build(200, "success", null);
	}
	
	/**
	 * 根据id查询task
	 * @param id 任务id
	 * @return
	 */
	@GetMapping("/{id}")
	public Result getTaskById(@PathVariable Long id) {
		List<Task> taskList = new ArrayList<>(tasks.values());
		for(Task task: taskList) {
			if(task.getId() == id) {
				return Result.build(200, "success", task);
			}
		}
		return Result.build(200, "There is no task with this id", null);
    }
	
	/**
	 * 根据id删除task
	 * @param id 任务id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Result delectTaskById(@PathVariable Long id) {
		if(tasks.size() == 0) {
			return Result.build(200, "Task list is empty", null);
		}
		for(Long i: tasks.keySet()) {
			Task task = tasks.get(i);
			if(task.getId() == id) {
				tasks.remove(i);
				return Result.build(200, "success", null);
			}
		}
		return Result.build(200, "There is no task with this id", null);
    }
	
}
