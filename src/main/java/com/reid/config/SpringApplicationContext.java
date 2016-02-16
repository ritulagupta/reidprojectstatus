package com.reid.config;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

public class SpringApplicationContext implements ApplicationContextAware{
	 
	 public static final String COMPONENT_NAME = "springApplicationContext";
	  
	 private static ApplicationContext applicationContext;

		// Implemented ApplicationContextAware methods ------------------------------------------------------------------------------------------
		
	  public void setApplicationContext(ApplicationContext context) throws BeansException {
	    applicationContext = context;
	  }
	  
	  // Public methods -----------------------------------------------------------------------------------------------------------------------

	  // Can return null depending on initialisation order or if requested bean does not exists/has not been initialised
	  public static Object getBean(String beanName) {
	    return applicationContext.getBean(beanName);
	  }

	  // Can return null depending on initialisation order
	  public static ApplicationContext getApplicationContext() {
	    return applicationContext;
	  }

	  // Can return null depending on initialisation order
	  public static ServletContext getServletContext() {
	    if (applicationContext != null) {
	      ServletContext servletContext = ((WebApplicationContext) applicationContext).getServletContext();
	      return servletContext;
	    }
	    return null;
	  }
}
