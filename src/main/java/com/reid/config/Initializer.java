package com.reid.config;

import org.springframework.web.WebApplicationInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer implements WebApplicationInitializer {
	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	  /**
	   * Default configuration file used if not specified in web.xml
	   */

	  public void onStartup(ServletContext container) throws ServletException {

	    // create the dispatcher servlet's Spring application context
	    AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();

	    ConfigurableEnvironment env = dispatcherContext.getEnvironment();
	    env.setActiveProfiles("mvc");

//	    dispatcherContext.scan("com.sandt.console.config.MongoConfig");
	    dispatcherContext.register(AppConfig.class);

	    // manage the lifecycle of the root application context
	    container.addListener(new ContextLoaderListener(dispatcherContext));

	    // register and map the dispatcher servlet
	    ServletRegistration.Dynamic dispatcher = container.addServlet(
	      DISPATCHER_SERVLET_NAME, new DispatcherServlet(dispatcherContext));
	    dispatcher.setLoadOnStartup(1);
	    dispatcher.addMapping("/");
	    SpringApplicationContext springApplicationContext = new SpringApplicationContext();
	    springApplicationContext.setApplicationContext(dispatcherContext);
	  }

}
