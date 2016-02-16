package com.reid.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reid.exception.ServiceException;
import com.reid.model.db.Building;
import com.reid.model.db.Employee;
import com.reid.model.db.ProjectTask;
import com.reid.model.db.TaskStatus;
import com.reid.model.domain.TaskStatusModel;

@Service
public class TaskStatusService extends BaseJpaService<TaskStatus, Long>{
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private EmployeeService employeeService;
	
	public TaskStatusModel createTaskAssigned(TaskStatusModel taskStatusModel) throws ServiceException {
		TaskStatus taskStatus =  create(toDb(taskStatusModel, new TaskStatus()));
		taskStatusModel.setId(taskStatus.getId());
		return taskStatusModel;
	}
	
	public TaskStatusModel updateTaskStatus(TaskStatusModel taskStatusModel) throws ServiceException {
		TaskStatus existingTaskStatus = getTaskStatusById(taskStatusModel.getId());
		if(existingTaskStatus != null){
			taskStatusModel.setVersion(existingTaskStatus.getVersion());
			TaskStatus taskStatus = toDb(taskStatusModel, existingTaskStatus);
			System.out.println("TSK STATUS "+ taskStatus);
			
			TaskStatus taskStatusUpdated =  update(taskStatus);
			return toDomain(taskStatusUpdated);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Task Status" + taskStatusModel.getId() + " not found ");
		}
	}
	
	public void removeTaskStatus(Long id) throws ServiceException {
		TaskStatus existingTaskAssigned = getTaskStatusById(id);
		if(existingTaskAssigned != null){
			remove(existingTaskAssigned);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Task Assigned" + id + " not found ");
		}
	}
	
	
	public TaskStatus getTaskStatusById(Long id) throws ServiceException {
		TaskStatus taskStatus = null;
	  try {
	    taskStatus =  findById(id);
    } catch(ServiceException ex){
      if( ex.getMessageCode().equals("NOT_FOUND")){
        return null;
      }
      if( ex.getCause() instanceof NoResultException ){
        return null;
      }
      throw ex;
    } 
	  
	  return taskStatus;
	  
	}
	
	public List<TaskStatusModel> getAllTasksAssigned() throws ServiceException {
		List<TaskStatusModel> taskStatusModels = new ArrayList<TaskStatusModel>();
		List<TaskStatus> tasksStatus =  findAll();
		
		for(int i =0;i<tasksStatus.size() ;i++) {
			System.out.println("TASK ASSIGNED " + tasksStatus.get(i).getId()  + tasksStatus.get(i).getProjectTask().getTaskName());
			taskStatusModels.add(toDomain(tasksStatus.get(i)));
		}
		return taskStatusModels;
	}
	
	public List<TaskStatusModel> getTaskAssignedByBuildingId(Long buildingId) throws ServiceException {
		List<TaskStatusModel> taskStatusModels = new ArrayList<TaskStatusModel>();

		EntityManager em = null;
    try {
      em = getEntityManager();
      Query taskAssignedyBuilding = em.createNamedQuery(TaskStatus.FIND_BY_BUILDING);
      taskAssignedyBuilding.setParameter("id", buildingId);
      List<TaskStatus> tasksStatus = findByQuery(taskAssignedyBuilding);
    	for(int i =0;i<tasksStatus.size() ;i++) {
  			System.out.println("TASK ASSIGNED " + tasksStatus.get(i).getId()  + tasksStatus.get(i).getProjectTask().getTaskName());
  			taskStatusModels.add(toDomain(tasksStatus.get(i)));
  		}
      
    }finally {
      if(em != null) {
        em.close();
      }
    }
    return taskStatusModels;
	}
	
	public List<TaskStatusModel>  getTaskAssignedByEmployeeId(Long employeeId) throws ServiceException {
		List<TaskStatusModel> taskStatusModels = new ArrayList<TaskStatusModel>();

		EntityManager em = null;
    try {
      em = getEntityManager();
      Query taskBuildingByEmployee = em.createNamedQuery(TaskStatus.FIND_BY_EMPLOYEE);
      taskBuildingByEmployee.setParameter("id", employeeId);
     
      List<TaskStatus> tasksStatus = findByQuery(taskBuildingByEmployee);
    
    	for(int i =0;i<tasksStatus.size() ;i++) {
  			System.out.println("TASK ASSIGNED " + tasksStatus.get(i).getId()  + tasksStatus.get(i).getProjectTask().getTaskName());
  			taskStatusModels.add(toDomain(tasksStatus.get(i)));
  		}
      return taskStatusModels;
      
    }finally {
      if(em != null) {
        em.close();
      }
    }
	}
	
	public List<TaskStatusModel> getTaskAssignedByBuildingAndEmployeeId(Long buildingId, Long employeeId) throws ServiceException {
		List<TaskStatusModel> taskStatusModels = new ArrayList<TaskStatusModel>();

		EntityManager em = null;
    try {
      em = getEntityManager();
      Query taskQuery = em.createNamedQuery(TaskStatus.FIND_BY_BUILDING_AND_EMPLOYEE);
      taskQuery.setParameter("employeeId", employeeId);
      taskQuery.setParameter("buildingId", buildingId);
      List<TaskStatus> tasksStatus = findByQuery(taskQuery);
      
      for(int i =0;i<tasksStatus.size() ;i++) {
  			System.out.println("TASK ASSIGNED " + tasksStatus.get(i).getId()  + tasksStatus.get(i).getProjectTask().getTaskName());
  			taskStatusModels.add(toDomain(tasksStatus.get(i)));
  		}
      return taskStatusModels;
      
    }finally {
      if(em != null) {
        em.close();
      }
    }
	}
	
	
	//Private methods ---------
	private TaskStatusModel toDomain(TaskStatus taskStatus) {
		TaskStatusModel taskStatusModel = new TaskStatusModel();
		taskStatusModel.setId(taskStatus.getId());
		
		ProjectTask projectTask = taskStatus.getProjectTask();
		if(projectTask != null) {
			taskStatusModel.setProjectTaskId(projectTask.getId());
			taskStatusModel.setTaskName(projectTask.getTaskName());
		}
		
		Building building = taskStatus.getBuilding();
		if(building != null) {
			taskStatusModel.setBuildingId(building.getId());
			taskStatusModel.setBuildingName(building.getBuildingName());
		}
		
		Employee employee = taskStatus.getEmployee();
		if(employee != null) {
			taskStatusModel.setEmployeeId(employee.getId());
			taskStatusModel.setFirstName(employee.getFirstName());
		}
		
		taskStatusModel.setStatusType(taskStatus.getStatusType());
		return taskStatusModel;
	}
	
	
	private TaskStatus toDb(TaskStatusModel taskStatusModel, TaskStatus taskStatus) throws ServiceException {
//		TaskStatus taskStatus = new TaskStatus();
//		taskStatus.setId(taskStatusModel.getId());
		
		System.out.println("TASK Status MODEL ID "+ taskStatusModel.getId());
		ProjectTask projectTask = null;
		Employee employee = null;
		Building building = null;
		
		try {
			projectTask = projectTaskService.getProjectById(taskStatusModel.getProjectTaskId());
			System.out.println("task id "+ projectTask.getId());
			
		  employee = employeeService.getEmployeeById(taskStatusModel.getEmployeeId());
		  System.out.println("employee id "+ employee.getId());
		  building = buildingService.getBuildingById(taskStatusModel.getBuildingId());	
		  System.out.println("building id "+ building.getId());
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("OPERATION FAILED ", "Error while retrieving from database ", ex);
		}
		if(projectTask != null) {
			taskStatus.setProjectTask(projectTask);
		}
		if(building != null){
			taskStatus.setBuilding(building);
		}
		if(employee != null) {
			taskStatus.setEmployee(employee);
		}
		
		taskStatus.setStatusType(taskStatusModel.getStatusType());
		System.out.println("task status " + taskStatus);
		return taskStatus;
	}
	

}
