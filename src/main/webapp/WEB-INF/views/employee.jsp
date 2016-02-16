<script src="static/script/frameworks/underscore.js"></script>
<script src="static/script/frameworks/backbone.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="employeeConfig"></div>

<script type="text/template" id="editEmployeeTemplate">
  <table class="table">
    <tr>
      <td class="subHeading">First Name </td>
      
      <td class="inputtd">
        <div class="inputdiv" data-validationkey="firstName">
          <input type="text" name="firstName" placeholder="First Name" value="<\%=employee.get('firstName')%>" >
        </div>
      </td>
    </tr>
    <tr>
      <td class="subHeading">Last Name </td>
      
      <td class="inputtd">
        <div class="inputdiv" data-validationkey="lastName">
          <input type="text" name="lastName" placeholder="Last Name" value="<\%=employee.get('lastName')%>" >
        </div>
      </td>
    </tr>
    <tr>
      <td class="inputtd">Address</td>
      
      <td class="inputtd">
        <div class="inputdiv" data-validationkey="address">
          <input type="text" name="address" placeholder="Address"  value="<\%=employee.get('address')%>" >
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


<script type="text/template" id="employeeListTemplate">
   <h3>Employees</h3>
    <form class="list-group-form">
        <a href="${bootstrap.pageRoot}#add" class="btn btn-primary ">Add Employee</a>
          <table class="table table-hover table-condensed table-striped table-bordered" width="80%">
  
            <thead>
                <tr>
                    <th>Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Address</th>
 					          <th></th>
          
                </tr>
            </thead>

          
             <\% _.each(employees,function(employee){%>
                 
                 <tr>
                    <td><\%=employee.get('id')%></td>
                    <td><\%=employee.get('firstName')%></td>
                    <td><\%=employee.get('lastName')%></td>
                    <td><\%=employee.get('address')%></td>
                    <td>
                      <a href="#edit/<\%=employee.get('id')%>">
                        <span class="glyphicon glyphicon-edit"></span>
                      </a>
                      <a href="#remove/<\%=employee.get('id')%>" data-url="${pageContext.request.contextPath}/${bootstrap.pageRoot}/#remove/<\%=employee.get('id')%>" data-id="<\%=employee.get('id')%>" id="delete" title="Delete" >
                        <span class="glyphicon glyphicon-trash"></span>
                      </a>
   
                    </td>
                </tr>
            <\%});	%>
         
        </table>
    </form>
</script>

<script src="static/script/models/employee.js"></script>
<script src="static/script/router/employee.js"></script>
<script src="static/script/view/employee.js"></script>


<script src="static/page/employee.js"></script>