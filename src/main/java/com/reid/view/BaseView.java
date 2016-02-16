package com.reid.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.InternalResourceView;

public class BaseView extends InternalResourceView{
	
  private static final String TEMPLATE = "/WEB-INF/views/index.jsp";
  private static final String PARTIAL_ATTRIBUTE = "partial";
  

  
  public BaseView() {
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    // Determine which request handle to expose to the RequestDispatcher.
    HttpServletRequest requestToExpose = getRequestToExpose(request);

    
    Map<String, Object> bootstrap = (Map<String, Object>)model.get("bootstrap");
    if( bootstrap == null ){
      bootstrap = new HashMap<String, Object>();
      model.put("bootstrap", bootstrap);
    }

    // Expose the model object as request attributes.
    exposeModelAsRequestAttributes(model, requestToExpose);

    // Expose helpers as request attributes, if any.
    exposeHelpers(requestToExpose);

    // Determine the path for the request dispatcher.
    String dispatcherPath = prepareForRendering(requestToExpose, response);

    requestToExpose.setAttribute("pageName", getBeanName());

    String accept = requestToExpose.getHeader("Accept");
    if( accept == null || ! accept.contains(PARTIAL_ATTRIBUTE) ){
      dispatcherPath = TEMPLATE;
    }
    // Obtain a RequestDispatcher for the target resource (typically a JSP).
    RequestDispatcher rd = getRequestDispatcher(requestToExpose, dispatcherPath);
    if (rd == null) {
      throw new ServletException("Could not get RequestDispatcher for [" + getUrl() +
          "]: Check that the corresponding file exists within your web application archive!");
    }

    // If already included or response already committed, perform include, else forward.
    if (useInclude(requestToExpose, response)) {
      response.setContentType(getContentType());
      if (logger.isDebugEnabled()) {
        logger.debug("Including resource [" + getUrl() + "] in InternalResourceView '" + getBeanName() + "'");
      }
      rd.include(requestToExpose, response);
    }

    else {
      // Note: The forwarded resource is supposed to determine the content type itself.
      exposeForwardRequestAttributes(requestToExpose);
      if (logger.isDebugEnabled()) {
        logger.debug("Forwarding to resource [" + getUrl() + "] in InternalResourceView '" + getBeanName() + "'");
      }
      rd.forward(requestToExpose, response);
    }
  }
  
  
}



