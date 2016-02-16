<script src="static/script/frameworks/underscore.js"></script>
<script src="static/script/frameworks/backbone.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- TODO confirmation box -->
 <!-- <div id="confirmDelete" title="Delete Building"></div>  -->

<div id="projectTaskConfig"></div>

<script type="text/template" id="editProjectTaskTemplate">
  <table class="table">
    <tr>
      <td class="subHeading">Task Name </td>
      
      <td class="inputtd">
        <div class="inputdiv" data-validationkey="taskName">
          <input type="text" name="taskName" placeholder="Task Name" value="<\%=projectTask.get('taskName')%>"  size="80">
        </div>
      </td>
    </tr>
   
    <tr>
      <td colspan="2" >
        <a href="#"  class="btn btn-primary" id="save" title="Save">Save</a>
        <a href="${bootstrap.pageRoot}" class="btn btn-primary" title="Cancel">Cancel</a>

      </td>

    </tr>
  </table>
</script>


<script type="text/template" id="projectTaskListTemplate">
   <h3>Project Tasks</h3>
    <form class="list-group-form">
        <a href="${bootstrap.pageRoot}#add" class="btn btn-primary ">Create Project Task</a>
          <table class="table table-hover table-condensed table-striped table-bordered" width="80%">
  
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Task Name</th>
                    <th></th>
          
                </tr>
            </thead>

          
             <\% _.each(projectTasks,function(projectTask){%>
                 
                 <tr>
                    <td><\%=projectTask.get('id')%></td>
                    <td><\%=projectTask.get('taskName')%></td>
         
                    <td>
                      <a href="#edit/<\%=projectTask.get('id')%>">
                        <span class="glyphicon glyphicon-edit"></span>
                      </a>
                      <a href="#remove/<\%=projectTask.get('id')%>" data-url="${pageContext.request.contextPath}/${bootstrap.pageRoot}/#remove/<\%=projectTask.get('id')%>" data-id="<\%=projectTask.get('id')%>" id="delete" title="Delete" >
                        <span class="glyphicon glyphicon-trash"></span>
                      </a>
   
                    </td>
                </tr>
            <\%});  %>
         
        </table>
    </form>
</script>

<script src="static/script/models/projectTask.js"></script>
<script src="static/script/router/projectTask.js"></script>
<script src="static/script/view/projectTask.js"></script>


<script src="static/page/projectTask.js"></script>