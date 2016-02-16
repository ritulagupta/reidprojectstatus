<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ include file="_standard_imports.jsp"%>
<!doctype html>

<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<html>
  <head>

    <%@ page isELIgnored="false" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Project Status</title>
    <meta name="viewport" content="width=device-width">
    <!-- <link rel="stylesheet" href="static/script/style/projectstatus.css"> -->
    <link rel="stylesheet/less" type="text/css" href="static/script/style/projectstatus.less" />
    <link rel="stylesheet" href="static/script/lib/bootstrap/css/bootstrap.min.css">

    <script src="static/script/frameworks/less-1.3.3.min.js"></script>
   
    <script src="static/script/lib/jquery/jquery-1.8.0.js"></script>
    <script src="static/script/lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="static/script/view/status.js"></script>

    <%@ include file="_bootstrap.jsp"%>
   
  </head>
	
 <body>
   	<jsp:include page="_header.jsp"/>

      
    <div style="margin-top:10px;padding:5px"> 
    <div id="errorbox"></div>
   	  <jsp:include page="${pageName}.jsp"/>
    </div>
   	<footer>
       <jsp:include page="_footer.jsp"/>
     </footer>
     <div id="flashMessage"></div>

     <!-- <div id="no-worky-warning">
       <div>
         <p>Reid Project Status requires JavaScript .
         <p>Please enable JavaScript in your settings.
       </div>
     </div> -->
  </body>

</html>
