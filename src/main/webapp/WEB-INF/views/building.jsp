<script src="static/script/frameworks/underscore.js"></script>
<script src="static/script/frameworks/backbone.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- TODO confirmation box -->
 <!-- <div id="confirmDelete" title="Delete Building"></div>  -->

<div id="buildingConfig"></div>

<script type="text/template" id="editBuildingTemplate">
  <table class="table">
    <tr>
      <td class="subHeading">Building Name </td>
      
      <td class="inputtd">
        <div class="inputdiv" data-validationkey="buildingName">
          <input type="text" name="buildingName" placeholder="Building Name" value="<\%=building.get('buildingName')%>" >
        </div>
      </td>
    </tr>
    <tr>
      <td class="inputtd">Address</td>
      
      <td class="inputtd">
        <div class="inputdiv" data-validationkey="building">
          <input type="text" name="address" placeholder="Address"  value="<\%=building.get('address')%>" >
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


<script type="text/template" id="buildingListTemplate">
   <h3>Buildings</h3>
    <form class="list-group-form">
        <div ><a href="${bootstrap.pageRoot}#add" class="btn btn-primary ">Create Building</a></div>
          <table class="table table-hover table-condensed table-striped table-bordered" width="80%">
  
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Building Name</th>
                    <th>Address</th>
 					          <th></th>
          
                </tr>
            </thead>

          
             <\% _.each(buildings,function(building){%>
                 
                 <tr>
                    <td><\%=building.get('id')%></td>
                    <td><\%=building.get('buildingName')%></td>
                    <td><\%=building.get('address')%></td>
                    <td>
                      <a href="#edit/<\%=building.get('id')%>">
                        <span class="glyphicon glyphicon-edit"></span>
                      </a>
                      <a href="#remove/<\%=building.get('id')%>" data-url="${pageContext.request.contextPath}/${bootstrap.pageRoot}/#remove/<\%=building.get('id')%>" data-id="<\%=building.get('id')%>" id="delete" title="Delete" >
                        <span class="glyphicon glyphicon-trash"></span>
                      </a>
   
                    </td>
                </tr>
            <\%});	%>
         
        </table>
    </form>
</script>

<script src="static/script/models/building.js"></script>
<script src="static/script/router/building.js"></script>
<script src="static/script/view/building.js"></script>


<script src="static/page/building.js"></script>