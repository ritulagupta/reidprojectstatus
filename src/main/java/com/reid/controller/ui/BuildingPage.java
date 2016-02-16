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

import com.reid.controller.api.BuildingController;
import com.reid.service.BuildingService;

@Controller
@RequestMapping("building")
public class BuildingPage {
	
	public static final UriComponents PAGE_URI_TEMPLATE = UriComponentsBuilder.fromUriString("building").build();  
	
	@Autowired
	private BuildingService buildingService;
	
	
	@ModelAttribute("bootstrap")
  public Map<String, ?> bootstrap(){
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("buildings", buildingService.getAllBuildings());
    model.put("pageRoot", PAGE_URI_TEMPLATE.toString());
  //  System.out.println("MODEL ATTRIBUTE DONE "+ model);
    return model;
  }
	
	@ModelAttribute("buildingkPageUri")
  public String buildingPageUri(){
    return BuildingPage.PAGE_URI_TEMPLATE.toString();
  }
	
	@ModelAttribute("buildingResourceUri")
  public String buildingResourceUri(){
    return BuildingController.LIST_URI_TEMPLATE.toString();
  }


  @RequestMapping(method=RequestMethod.GET)
  public String building() {
    return "building";
  }
}
