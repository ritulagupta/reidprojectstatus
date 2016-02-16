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


import com.reid.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeePage {
	
	public static final UriComponents PAGE_URI_TEMPLATE = UriComponentsBuilder.fromUriString("employee").build();  
		
	
	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute("bootstrap")
  public Map<String, ?> bootstrap(){
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("employees", employeeService.getAllEmployees());
    model.put("pageRoot", PAGE_URI_TEMPLATE.toString());
   // System.out.println("MODEL ATTRIBUTE DONE "+ model);
    return model;
  }
	
	@ModelAttribute("employeePageUri")
  public String projectTaskPageUri(){
    return EmployeePage.PAGE_URI_TEMPLATE.toString();
  }
	

  @RequestMapping(method=RequestMethod.GET)
  public String employee() {
    return "employee";
  }
}
