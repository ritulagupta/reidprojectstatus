package com.reid.service;


import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.reid.exception.ServiceException;
import com.reid.model.db.ProjectTask;

@Service
public class ProjectTaskService extends BaseJpaService<ProjectTask , Long> {
	
	public void createProjectTask(ProjectTask task) throws ServiceException {
		create(task);
	}
	
	public void updateProjectTask(ProjectTask task) throws ServiceException {
		if(getProjectById(task.getId()) != null){
			update(task);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Task" + task.getId() + " not found ");
		}
		
	}
	
	
	public void removeProject(Long id) throws ServiceException {
		ProjectTask existingTask = getProjectById(id);
		if(existingTask != null){
			remove(existingTask);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Task" + id + " not found ");
		}
	}
	
	
	public ProjectTask getProjectById(Long id) throws ServiceException {
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
	
	public List<ProjectTask> getAllProjectTasks() throws ServiceException {
		List<ProjectTask> tasks =  findAll();
		for(int i =0;i<tasks.size() ;i++) {
			System.out.println("TASK " + tasks.get(i).getId()  + tasks.get(i).getTaskName());
		}
		return tasks;
	}

}
