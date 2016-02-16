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
import com.reid.model.db.Employee;
import com.reid.service.EmployeeService;

@Controller
@RequestMapping("api/employee")
public class EmployeeController {
	
	public static final UriComponents LIST_URI_TEMPLATE = UriComponentsBuilder.fromUriString("api/employee").build();
  
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<Employee> getTasks() throws ServiceException{
		List<Employee> employees =  employeeService.findAll();
		if(employees == null) {
			throw new ServiceException("EMPLOYEE RETRIEVAL ERROR ", "cannot retrieve");
		}
		return employees;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Employee addEmployee(@RequestBody Employee employee) throws ServiceException {

		employeeService.create(employee);
		return employee;
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	@ResponseBody
	public Employee updateTask(@RequestBody Employee employee) throws ServiceException {
		employeeService.update(employee);
		return employee;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  @ResponseBody
  public void remove(final @PathVariable Long id) throws ServiceException {
		employeeService.removeEmployee(id);
  }

}
