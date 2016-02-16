<script src="static/script/frameworks/underscore.js"></script>
<script src="static/script/frameworks/backbone.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div id="taskStatusConfig"></div>

<script type="text/template" id="editTaskStatusTemplate">


<table class="table table-hover table-condensed ">

  <tr>
    <!-- TODO (RD) - Using strong maybe naughty -->
    <td><strong>Task Name:</strong></td>
    <td class="inputtd">

      <select name="taskName" id="taskName" class="taskName"> 
        <option value="-">Select Task</option>
         
        <\% _.each(bootstrap.projectTasks,function(task){%>
          <option value="<\%=task.id%>" <\%=(task.id == taskStatus.get('projectTaskId')) ? 'selected' : ''%>><\%=task.taskName%></option>
          
         <\%}); %>
       </select>
      </td>
    </tr>
    <tr>
      <!-- TODO (RD) - Using strong maybe naughty -->
      <td><strong>Building Name:</strong></td>
      <td class="inputtd">

        <select name="buildingName" id="buildingName" class="buildingName"> 
          <option value="-">Select Building</option>
           
          <\% _.each(bootstrap.buildings,function(building){%>
            <option value="<\%=building.id%>"  <\%=(building.id == taskStatus.get('buildingId')) ? 'selected' : ''%>><\%=building.buildingName%></option>
           <\%}); %>
         </select>
        </td>
      </tr>

      <tr>
        <!-- TODO (RD) - Using strong maybe naughty -->
        <td><strong>Employee Name:<\%=taskStatus.get('employeeId')%></strong></td>
        <td class="inputtd">

          <select name="employee" id="employee"  class="employee"> 
            <option value="-">Select Employee</option>
             
            <\% _.each(bootstrap.employees,function(employee){%>
              <option value="<\%=employee.id%>" <\%=(employee.id == taskStatus.get('employeeId')) ? 'selected' : ''%>><\%=employee.firstName%></option>
             <\%}); %>
           </select>
          </td>
        </tr>

      <tr>
        <!-- TODO (RD) - Using strong maybe naughty -->
        <td><strong>Task Status</strong></td>
        <td class="inputtd">

          <select name="taskStatus" id="taskStatus" class="taskStatus" > 
            <option value="-">Select Status</option>
             
            <\% _.each(bootstrap.statusTypes,function(statusType){%>
              <option value="<\%=statusType%>" <\%=(statusType == taskStatus.get('statusType')) ? 'selected' : ''%>><\%=statusType%></option>
             <\%}); %>
           </select>
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


<script type="text/template" id="taskStatusListTemplate">
   <h3>Tasks Assigned</h3>
    <form class="list-group-form">
        <a href="${bootstrap.pageRoot}#add" class="btn btn-primary ">Assign Task</a>
        <div class="pageHeader">Select below to view task status for particular employee and task</div>
        <table>
         <tr>
          <td>Employee Name :</td>
          <td> 
            <select name="employeeNameGetList" id="employeeNameGetList"  class="employeeNameGetList"> 
              <option value="-">Select Employee</option>
              <\% _.each(bootstrap.employees,function(employee){%>
              <option value="<\%=employee.id%>" <\%=(employee.id == selectedEmployeeId) ? 'selected' : ''%>><\%=employee.firstName%></option>
               <\%}); %>
              </select>
            </td>
         
            <td> &nbsp;&nbsp;Task Name : </td>
            <td>
              <select name="taskNameGetList" id="taskNameGetList"  class="taskNameGetList"> 
                <option value="-">Select Task</option>
                  <\% _.each(bootstrap.projectTasks,function(task){%>
                <option value="<\%=task.id%>" <\%=(task.id == selectedTaskId) ? 'selected' : ''%> ><\%=task.taskName%></option>
                 <\%}); %>
              </select>
            </td>
          </tr>
        </table>
        <table class="table table-hover table-condensed table-striped table-bordered" width="80%">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Task Name</th>
                    <th>Building Name</th>
                    <th>Employee Name</th>
                    <th>Status</th>
 					          <th></th>
          
                </tr>
            </thead>

          
             <\% _.each(tasksStatus,function(task){%>
                 
                 <tr>
                    <td><\%=task.get('id')%></td>
                    <td><\%=task.get('taskName')%></td>
                    <td><\%=task.get('buildingName') %></td>
                    <td><\%=task.get('firstName')%></td>
                    <td><\%=task.get('statusType')%></td>
                    <td>
                   
                      <a href="#edit/<\%=task.get('id')%>">
                        <span class="glyphicon glyphicon-edit"></span>
                      </a>
                      <a href="#remove/<\%=task.get('id')%>" data-url="${pageContext.request.contextPath}/${bootstrap.pageRoot}/#remove/<\%=task.get('id')%>" data-id="<\%=task.get('id')%>" id="delete" title="Delete">
                         <span class="glyphicon glyphicon-trash"></span>
                      </a>

   
                    </td>
                </tr>
            <\%});	%>
         
        </table>
    </form>
</script>


<script src="static/script/models/taskStatus.js"></script>
<script src="static/script/router/taskStatus.js"></script>
<script src="static/script/view/taskStatus.js"></script>


<script src="static/page/taskStatus.js"></script>