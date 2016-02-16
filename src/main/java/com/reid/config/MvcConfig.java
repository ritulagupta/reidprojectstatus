package com.reid.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.View;

import com.reid.view.BaseView;

import java.util.*;

@Profile("mvc")
@EnableWebMvc
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/history").setViewName("_history");
  }

  @Bean
  public View jsonView() {
    MappingJackson2JsonView view = new MappingJackson2JsonView();
    return view;
  }

  @Bean
  public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
    final ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    final List<View> defaultViews = new ArrayList<View>();

    defaultViews.add(jsonView());
    resolver.setDefaultViews(defaultViews);
    resolver.setUseNotAcceptableStatusCode(true);
    resolver.setOrder(1);
    return resolver;
  }

  @Bean
  public InternalResourceViewResolver internalResourceViewResolver() {
    final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    resolver.setOrder(2);
    resolver.setViewClass(BaseView.class);
    resolver.setExposedContextBeanNames(new String[] {"props"});
    return resolver;
  }
  
 
  
}

