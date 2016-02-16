package com.reid.controller.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.reid.controller.api.ProjectTaskController;
import com.reid.service.ProjectTaskService;

@Controller
@RequestMapping("projectTask")
public class ProjectTaskPage {
	
	public static final UriComponents PAGE_URI_TEMPLATE = UriComponentsBuilder.fromUriString("projectTask").build();  
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	
	@ModelAttribute("bootstrap")
  public Map<String, ?> bootstrap(){
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("projectTasks", projectTaskService.getAllProjectTasks());
    model.put("pageRoot", PAGE_URI_TEMPLATE.toString());
   // System.out.println("MODEL ATTRIBUTE DONE "+ model);
    return model;
  }
	
	@ModelAttribute("projectTaskPageUri")
  public String projectTaskPageUri(){
    return ProjectTaskPage.PAGE_URI_TEMPLATE.toString();
  }
	
	@ModelAttribute("projectTaskResourceUri")
  public String projectTaskResourceUri(){
    return ProjectTaskController.LIST_URI_TEMPLATE.toString();
  }


  @RequestMapping(method=RequestMethod.GET)
  public String projectTask() {
    return "projectTask";
  }
}
