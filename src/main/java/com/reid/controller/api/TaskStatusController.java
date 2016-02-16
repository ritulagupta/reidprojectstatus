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
import com.reid.model.db.TaskStatus;
import com.reid.model.domain.TaskStatusModel;
import com.reid.service.TaskStatusService;

@Controller
@RequestMapping("api/taskStatus")
public class TaskStatusController {
	public static final UriComponents LIST_URI_TEMPLATE = UriComponentsBuilder.fromUriString("api/taskStatus").build();
  
	@Autowired
	private TaskStatusService taskStatusService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<TaskStatusModel> getTaskAssigned() throws ServiceException{
		System.out.println("getting list of tasks 	" +  taskStatusService.findAll());
		return taskStatusService.getAllTasksAssigned();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public TaskStatusModel addTaskAssigned(@RequestBody TaskStatusModel taskStatusModel) {
		System.out.println("add project task");
		taskStatusService.createTaskAssigned(taskStatusModel);
		return taskStatusModel;
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	@ResponseBody
	public TaskStatusModel updateTask(@RequestBody TaskStatusModel taskStatusModel) throws ServiceException{
		System.out.println("IN UPDYAED");
		if(taskStatusService.getTaskStatusById(taskStatusModel.getId()) == null){
			throw new ServiceException("NOT_FOUND", "Task Sttus +" + taskStatusModel.getId() + " not found");
		}
		System.out.println("update project task");
		return taskStatusService.updateTaskStatus(taskStatusModel);
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  @ResponseBody
  public void remove(final @PathVariable Long id) {
		taskStatusService.removeTaskStatus(id);
  }
	
	
	@RequestMapping(value="/employee/{employeeId}", method=RequestMethod.GET)
  @ResponseBody
  public List<TaskStatusModel> getTaskAssignedToEmployee(final @PathVariable Long employeeId) throws ServiceException{
		List<TaskStatusModel> tasksAssigned = taskStatusService.getTaskAssignedByEmployeeId(employeeId);
		return tasksAssigned;
  }
	
	@RequestMapping(value="/building/{buildingId}", method=RequestMethod.GET)
  @ResponseBody
  public List<TaskStatusModel> getTaskAssignedToBuilding(final @PathVariable Long buildingId) throws ServiceException{
		List<TaskStatusModel> tasksAssigned = taskStatusService.getTaskAssignedByBuildingId(buildingId);
		return tasksAssigned;
  }
	
	@RequestMapping(value="/employee/{employeeId}/building/{buildingId}", method=RequestMethod.GET)
  @ResponseBody
  public List<TaskStatusModel> getTaskAssignedToBuildingAndEmployee(final @PathVariable Long employeeId,
  																															 	  final @PathVariable Long buildingId) throws ServiceException{
		List<TaskStatusModel> tasksAssigned = taskStatusService.getTaskAssignedByBuildingAndEmployeeId(buildingId, employeeId);
		return tasksAssigned;
  }
}
