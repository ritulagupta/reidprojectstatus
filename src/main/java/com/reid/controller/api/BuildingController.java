package com.reid.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.reid.model.db.Building;
import com.reid.service.BuildingService;


@Controller
@RequestMapping("api/building")
public class BuildingController {
	
public static final UriComponents LIST_URI_TEMPLATE = UriComponentsBuilder.fromUriString("api/building").build();
  
	
	@Autowired
	private BuildingService buildingService;
	
	@RequestMapping(method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Building> getBuildings() throws ServiceException{
		
		List<Building> buildingsList =  buildingService.findAll();
		return buildingsList;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Building addBuilding(@RequestBody Building building) throws ServiceException {
		buildingService.create(building);
		return building;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public Building updateBuilding(@RequestBody Building building) throws ServiceException{
		buildingService.update(building);
		return building;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  @ResponseBody
  public void remove(final @PathVariable Long id) throws ServiceException{
		System.out.println("REmoving building "+id);
		buildingService.removeBuilding(id);
  }

}
