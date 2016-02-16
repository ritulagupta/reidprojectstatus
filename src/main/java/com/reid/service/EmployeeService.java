package com.reid.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.reid.exception.ServiceException;
import com.reid.model.db.Employee;

@Service
public class EmployeeService extends BaseJpaService<Employee , Long> {
	
	public void createEmployee(Employee employee) throws ServiceException {
		create(employee);
	}
	
	public void updateEmployee(Employee employee) throws ServiceException {
		if(getEmployeeById(employee.getId()) != null){
			update(employee);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Employee" +employee.getId() + " not found ");
		}
		
	}
	
	
	public void removeEmployee(Long id) throws ServiceException {
		Employee existingEmployee = getEmployeeById(id);
		if(existingEmployee != null){
			remove(existingEmployee);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Employee" + id + " not found ");
		}
	}
		
	
	public Employee getEmployeeById(Long id) throws ServiceException {
	 try {
		 return findById(id);
    } catch(ServiceException ex){
      if( ex.getMessageCode().equals("NOT_FOUND")){
        return null;
      }
      if( ex.getCause() instanceof NoResultException ){
        return null;
      }
      throw ex;
    } 
	}
	
	public List<Employee> getAllEmployees() throws ServiceException {
		List<Employee> employees =  findAll();
		for(int i =0;i<employees.size() ;i++) {
			System.out.println("EMPLOYEE " + employees.get(i).getId()  + employees.get(i).getFirstName());
		}
		return employees;
	}

}
