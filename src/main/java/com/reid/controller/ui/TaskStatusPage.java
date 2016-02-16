package com.reid.controller.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.reid.controller.api.TaskStatusController;
import com.reid.model.db.StatusType;
import com.reid.service.BuildingService;
import com.reid.service.EmployeeService;
import com.reid.service.ProjectTaskService;
import com.reid.service.TaskStatusService;

@Controller
@RequestMapping("/taskStatus")
public class TaskStatusPage {
	
	public static final UriComponents PAGE_URI_TEMPLATE = UriComponentsBuilder.fromUriString("taskStatus").build();  

	@Autowired
	private TaskStatusService taskStatusService;
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private EmployeeService employeeService;
	
  @RequestMapping(value = "/statusType",method = RequestMethod.GET)
  public List<String> getStatusTypes() {
    StatusType[] statusTypes =  StatusType.values();
    List<String> statusTypesStr = new ArrayList<String>();
    for(StatusType statusType : statusTypes) {
    	statusTypesStr.add(statusType.toString());
    }
    return statusTypesStr;
  }
  
  @ModelAttribute("bootstrap")
  public Map<String, ?> bootstrap(){
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("projectTasks", projectTaskService.getAllProjectTasks());
    model.put("statusTypes", getStatusTypes());
    model.put("employees", employeeService.getAllEmployees());
    model.put("buildings", buildingService.getAllBuildings());
    model.put("pageRoot", PAGE_URI_TEMPLATE.toString());
    return model;
  }
	
	@ModelAttribute("taskStatusPageUri")
  public String projectTaskPageUri(){
    return TaskStatusPage.PAGE_URI_TEMPLATE.toString();
  }
	
	@ModelAttribute("taskStatusResourceUri")
  public String projectTaskResourceUri(){
    return TaskStatusController.LIST_URI_TEMPLATE.toString();
  }


  @RequestMapping(method=RequestMethod.GET)
  public String taskStatus() {
    return "taskStatus";
  }
}
