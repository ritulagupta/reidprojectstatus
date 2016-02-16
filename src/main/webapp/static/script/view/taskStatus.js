var tasksStatus = {};


var TaskStatusListView = Backbone.View.extend ({

  el : '#taskAssignedConfig',// The element we defined in HTML
  initialize : function() {
    console.log("this.model ", this.model);
    var self = this;
    this.model.taskStatusCollection.fetch({
      success : function(e) {
        console.log("got taskStatus count INITIALIZE", self.model);
        window.location.hash = "#";
        self.render()
      }
    });
     this.model.taskStatusCollection.bind('change', this.render());
     this.selectedEmployeeId = null;
     this.selectedTaskId = null;
  },

  events: {
    'click a.delete' : 'remove',
    'change select.employeeNameGetList' : '_getListByEmployee',
    'change select.taskNameGetList' :'_getListByTask'
  },
  render: function () {
      console.log("selectedEmployeeId in render", this.selectedEmployeeId);
      console.log("render list");
      var editing = this.editedTaskStatus !== null;
      this.editedTaskStatusView = null;
    

      var self = this;// Saving the scope object

      var taskStatusList = this.model.taskStatusCollection.models;

      console.log("got taskStatus count RENDER", this.model);
      var taskStatusTemplate = _.template($('#taskStatusListTemplate').html(), { 
                                          tasksStatus: taskStatusList ,
                                          selectedEmployeeId : self.selectedEmployeeId,
                                          selectedTaskId : self.selectedTaskId
                                        });
     
 
       _.each(taskStatusList, function (taskStatus) {
        if (self.editedTaskStatus === taskStatus) {
          console.log("match found--");
          var editview = new EditTaskStatusView({
            model: {
              taskStatus : taskStatus,
              taskStatusList : self.model.taskAssignedCollection,
             
            }
          });

          self.editedTaskStatusView = editview;
          editview.render();
          } 
      });
       this.$el.html(taskStatusTemplate);
      return this;
    },
    add : function() {
      var taskStatus = new TaskStatus();
      console.log("this ", this.model);
      this.model.taskStatusCollection.add(taskStatus);
      this.editedTaskStatus = taskStatus;
      this.render();
    },
    edit : function(id) {
      console.log("IN VIEW EDIT EditTaskStatusView");
      var taskStatus = this._get(id);
      console.log("got taskStatus ", taskStatus);
       if (taskStatus) {
         this.taskStatus = taskStatus;

         console.log("taskStatus in edit biew ", this.taskStatus);
         this.render();
       }

    },
    _get : function(id) {
      console.log("ID ", id);
      var taskStatus = _.find(this.model.taskStatusCollection.models, function (taskStatus) {
        console.log("in VIEW find", taskStatus);
        if (taskStatus.get('id') == id) {
          return taskStatus;
        }
      });
      return taskStatus;
    },
    _getListByEmployee : function() {
       var employeeId = this._getInputValue('.employeeNameGetList');
       this.selectedEmployeeId = employeeId;
       var taskId = this._getInputValue('.taskNameGetList');
       this.selectedTaskId = taskId;
       console.log("selectedEmployeeId in _getListByEmployee", this.selectedEmployeeId);

       var self = this;
       this.model.taskStatusCollection.fetch({
          success : function(data) {

           if(employeeId && taskId) {
             var taskStatusByEmployeAndTask = data.filter(function(model) {
                                            return (model.get('employeeId') == employeeId && 
                                                    model.get('projectTaskId') == taskId);
                                           });
             var taskStatusCollection = new TaskStatusCollection(taskStatusByEmployeAndTask);
             self.model.taskStatusCollection = taskStatusCollection;
           }
           else if(employeeId && !taskId ){
             var taskStatusByEmployee = data.filter(function(model) {
                                           return model.get('employeeId') == employeeId;
                                         });          
                                        
             var taskStatusCollection= new TaskStatusCollection(taskStatusByEmployee);
           
             console.log(taskStatusCollection );
             self.model.taskStatusCollection = taskStatusCollection;
           }
          
           self.render();
          }
       })
    },
    _getListByTask: function() {
       var taskId = this._getInputValue('.taskNameGetList');
       this.selectedTaskId = taskId;
       var self = this;
       console.log("taskId "+ taskId +"test",this.model.taskStatusCollection);
       this.model.taskStatusCollection.fetch({
          success : function(data) {
           if(taskId) {
             var taskStatusByTasks= data.filter(function(model) {
                                            return model.get('projectTaskId') == taskId;
                                           });
            
             var taskStatusCollection= new TaskStatusCollection(taskStatusByTasks);
             console.log(taskStatusCollection );
             self.model.taskStatusCollection = taskStatusCollection;
            }
          
           self.render();
          }
       })

    },
    postSuccess : function(data){
      console.log("fetched successfully",data);

    },
    _getInputValue: function(selector){
     var val = this.$(selector).val();
     return val === '-' ? null : val;
    },
    _getInputText: function(selector){
     var text = this.$(selector).text();
     console.log("text", text);
     return text === '-' ? null : text;
    },
    remove: function(id) {
      var taskStatus = this._get(id);
      console.log("REmove view ", taskStatus);
      if (taskStatus) {
        var self = this;
        taskStatus.destroy({
          wait: true,
          success: function () {
            console.log("destroy taskStatus");
            self.render();
          },
          error: function(model,response) {
            if (response) {
  
              $("#errorbox").html(response.statusText);
      
            }
          }
        });
      }
    }
});


var EditTaskStatusView = Backbone.View.extend({

  el : '#taskStatusConfig',

  options : {
    debug : true
  },

  events: {
    'click a#save' : '_save',
    'change select.taskName': '_changeTaskName',
    'change select.buildingName': '_changeBuildingName',
    'change select.employee': '_changeEmployee',
    'change select.taskStatus': '_changeTaskStatus'
  },


  // contains any validation messages
  validation : {},
  error : null,

  initialize: function () {

    _.bindAll(this, '_save','_onSaveSuccess','_onSaveError', 'render','close','_changeTaskName','_changeBuildingName','_changeEmployee','_changeTaskStatus' );
    this.model.taskStatus.bind("destroy", this.close, this);
    
  },

  render: function() {
    console.log("IN RENDER", this.model.taskStatus);
    var self = this;
    var taslStatusTemplate = _.template($('#editTaskStatusTemplate').html(), 
                                  { taskStatus : self.model.taskStatus ,
                                    taskStatusList : self.model.taskStatus});
    this.$el.html(taslStatusTemplate);
    return this;
  },
  _save : function() {
    console.log("SAVE METHOD", this.model.taskStatus);
    console.log("is new ", this.model.taskStatus.isNew());
    var method ='POST';
    if(this.model.taskStatus.id) {
      method='PUT';
    }
    console.log("method ", method);
    this.model.taskStatus.save({},{

       type : method,
       success : this._onSaveSuccess,
       error : this._onSaveError
     });
  },
  _onSaveSuccess : function(model,response) {
    console.log("success",this.model);

    var self = this;
    this.taskStatusListView = new TaskStatusListView({
      el: $('#taskStatusConfig'),
      model: {
        taskStatusCollection : self.model.taskStatusCollection
      }
    });
    
     window.location.hash = "#";
  },

  _onSaveError : function(model,response) {
    if(response) {
      $("#errorbox").html(response.statusText);
    } 
        
  },

  edit : function(id) {
    var taskStatus = this._get(id);
    console.log("got taskStatus ", taskStatus);
    console.log("updated taskStatus ", this.model.taskStatus);
     if (taskStatus) {
       this.model.taskStatus = taskStatus;
       console.log("taskStatus  in edit ", this.model.taskStatus);
       this.render();
     }
  },
  _get : function(id) {
    console.log("ID ", id);
    var taskStatus = _.find(this.model.taskStatusCollection.models, function (taskStatus) {
      console.log("in find", taskStatus);
      if (taskStatus.get('id') == id) {
        return taskStatus;
      }
    });
    return taskStatus;
  },
  _changeTaskName : function(e) {
    console.log("task name changed");
    var projectTaskId = this._getInputValue('.taskName');
    var taskName = this._getInputText(".taskName option:selected");
    this.model.taskStatus.set("projectTaskId",projectTaskId);
    this.model.taskStatus.set("taskName",taskName);
    console.log("task name changed", this.model);
  },
   _changeBuildingName : function(e) {
    console.log("buildingName name changed");
    var buildingId = this._getInputValue('.buildingName');
    var buildingName = this._getInputText(".buildingName option:selected");
    this.model.taskStatus.set("buildingId",buildingId);
    this.model.taskStatus.set("buildingName",buildingName);
    console.log("task name changed", this.model);
  },
   _changeEmployee : function(e) {
    console.log("employee name changed");
    var employeeId = this._getInputValue('.employee');
    var employeeName = this._getInputText(".employee option:selected");
    this.model.taskStatus.set("employeeId",employeeId);
    this.model.taskStatus.set("firstName",employeeName);
    console.log("task name changed", this.model);
  },
  _changeTaskStatus : function(e) {
    console.log("task status name changed");
    var taskStatus = this._getInputValue('.taskStatus');
    this.model.taskStatus.set("statusType",taskStatus);
    //this.model.taskStatus.set("employeeName",employeeName);
    console.log("task name changed", this.model);
  },
  _changed:function(e) {
    console.log("in change ");
   var name = $(e.currentTarget).attr('name');
   var val = $(e.currentTarget).val();

      
     var id = this.model.taskStatus.get('id');
     console.log("ID ", id);
     if (id) {
      console.log("Id exists");
       this.model.taskStatus.id = id;
     }
     var set = this.model.taskStatus.set(name , val, {
       //error : this._onSetError
     });
     console.log("this.model.taskStatus ", this.model.taskStatus);
     
 },
 _getInputValue: function(selector){
  var val = this.$(selector).val();
  return val === '-' ? null : val;
 },
 _getInputText: function(selector){
  var text = this.$(selector).text();
  console.log("text", text);
  return text === '-' ? null : text;
 },
 close:function () {
    $(this.el).unbind();
    $(this.el).remove();
  }


});