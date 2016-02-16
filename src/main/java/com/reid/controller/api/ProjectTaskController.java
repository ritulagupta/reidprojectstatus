package com.reid.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.reid.exception.ServiceException;
import com.reid.model.db.ProjectTask;
import com.reid.service.ProjectTaskService;

@Controller
@RequestMapping("api/projectTask")
public class ProjectTaskController {
	
	public static final UriComponents LIST_URI_TEMPLATE = UriComponentsBuilder.fromUriString("api/projectTask").build();
  
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<ProjectTask> getTasks() throws ServiceException{
		List<ProjectTask> tasks=  projectTaskService.findAll();
		if(tasks != null) {
			System.out.println("Size of tasks "+ tasks.size());
		}
		else {
			throw new ServiceException("TASK ERROR ", "cannot retrieve");
		}
		return tasks;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ProjectTask addTask(@RequestBody ProjectTask task) throws ServiceException {
		System.out.println("add project task");
		projectTaskService.create(task);
		
		return task;
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	@ResponseBody
	public ProjectTask updateTask(@RequestBody ProjectTask task) throws ServiceException{
		System.out.println("update project task");
		projectTaskService.update(task);
		return task;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  @ResponseBody
  public void remove(final @PathVariable Long id) throws ServiceException{
		projectTaskService.removeProject(id);
  }

	
	
}
