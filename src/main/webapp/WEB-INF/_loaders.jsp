<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%-- The release profile should have this file replaced in the war with tags 
     to load compiled/optimised css and js directly --%>

<!-- styles for the basic page framework -->
<link rel="stylesheet/less" href="static/style/console.less">
<!-- styles for the actual page -->
<link rel="stylesheet/less" href="static/style/${pageName}.less">

<%@include file="_polyfills.jsp"%>

<%-- less.js includes must happen after all less files included --%>
<script>
less = {
  env: "development", 
  async: false,
  fileAsync: false,
  poll: 1000,
  functions: {},
  dumpLineNumbers: "all", 
  relativeUrls: false
};
</script>
<script src="static/script/frameworks/less-1.3.3.js"></script>

<%-- include require.js without the data-main attribute to avoid races. --%>
<script src="static/script/frameworks/requirejs/require.js"></script>
<script src="static/script/require.conf.js"></script>

<%-- we include config or bootstrap data that will get passed to the page --%>
<%-- functions. --%>

<%@include file="_bootstrap.jsp"%>
