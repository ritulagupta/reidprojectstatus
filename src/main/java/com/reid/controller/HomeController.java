package com.reid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class HomeController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(ModelAndView mav) {
		System.out.println("HOME HOME");
		mav.setViewName("home");
		
		return "home";
		
	}

  @RequestMapping(value="notFound",method=RequestMethod.GET)
  public ModelAndView showNotFoundPage(ModelAndView mav) {
  	System.out.println("NOT FOUND ");
    mav.setViewName("not_found");
		
		return mav;
  }

}
