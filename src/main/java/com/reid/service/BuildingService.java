package com.reid.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.reid.exception.ServiceException;
import com.reid.model.db.Building;

@Service
public class BuildingService extends BaseJpaService<Building , Long> {
	
	public void createBuilding(Building building) throws ServiceException {
		create(building);
	}
	
	public void updateBuilding(Building building) throws ServiceException {
		if(getBuildingById(building.getId()) != null){
			update(building);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Building" + building.getId() + " not found ");
		}
	}
	
	public void removeBuilding(Long id) throws ServiceException {
		Building existingBuilding = getBuildingById(id);
		if(existingBuilding != null){
			remove(existingBuilding);
		}
		else {
			throw new ServiceException("NOT_FOUND", "Building" + id + " not found ");
		}
	}
	
	
	public Building getBuildingById(Long id) throws ServiceException {
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
	
	public List<Building> getAllBuildings() throws ServiceException {
		List<Building> buildings =  findAll();
		return buildings;
	}

}
