var projectTasks = {};


var ProjectTaskListView = Backbone.View.extend ({

  el : '#projectTaskConfig',// The element we defined in HTML
  initialize : function() {
    console.log("this.model ", this.model);
    var self = this;
    this.model.projectTaskCollection.fetch({
      success : function(e) {
        window.location.hash = "#";
        self.render()
      }
    });
  },

  events: {
    'click a.delete' : 'remove'
  },
  render: function () {

      console.log("render lisr");
      var editing = this.editedProjectTask !== null;
      this.editedProjectTaskView=null;

      var self = this;// Saving the scope object
    
      var projectTaskList = this.model.projectTaskCollection.models;
  
      var projectTaskTemplate = _.template($('#projectTaskListTemplate').html(), { projectTasks: projectTaskList });
     
 
       _.each(projectTaskList, function (projectTask) {
      
        if (self.editedProjectTask === projectTask) {
 
          var editview = new EditProjectTaskView({
            model: {
              projectTask : projectTask,
              projectTaskList : self.model.projectTaskCollection
            }
          });

          self.editedProjectTask = editview;
          editview.render();
     } 
      });
       this.$el.html(projectTaskTemplate);
      return this;
    },
    add : function() {
      var projectTask = new projectTask();
      console.log("this ", this.model);
      this.model.projectTaskCollection.add(projectTask);
      this.editedProjectTask = projectTask;
      this.render();
    },
    edit : function(id) {
      var projectTask = this._get(id);
      if (projectTask) {
        this.projectTask = projectTask;
        this.render();
      }
      // this.editedBuilding = building;
      // this.render();
    },
    _get : function(id) {
      var projectTask = _.find(this.model.projectTaskCollection.models, function (projectTask) {
        if (projectTask.get('id') == id) {
          return projectTask;
        }
      });
      return projectTask;
    },

    remove: function(id) {
      var projectTask = this._get(id);

      if (projectTask) {
        var self = this;
        projectTask.destroy({
          wait: true,
          success: function () {
            console.log("destroy projectTask");
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


var EditProjectTaskView = Backbone.View.extend({

  el : '#projectTaskConfig',

  options : {
    debug : true
  },

  events: {
    'click a#save' : '_save',
    'change input' :'_changed'
  },


  // contains any validation messages
  validation : {},
  error : null,

  initialize: function () {
     _.bindAll(this, '_save','_onSaveSuccess','_onSaveError', 'render','close' );

   this.model.projectTask.bind("destroy", this.close, this);
    
  },

  render: function() {
    console.log("IN RENDER", this.model.projectTask);
    var projectTaskTemplate = _.template($('#editProjectTaskTemplate').html(), 
                             { projectTask : this.model.projectTask ,
                              projectTaskList : this.model.projectTaskList});
    this.$el.html(projectTaskTemplate);
    return this;
  },
  _save : function() {
    var method ='POST';
    if(this.model.projectTask.id) {
      method='PUT';
    }
    this.model.projectTask.save({},{
       type : method,
       success : this._onSaveSuccess,
       error : this._onSaveError
     });
  },
  _onSaveSuccess : function(model,response) {
    var self = this;
    this.projectTaskListView = new ProjectTaskListView({
      el: $('#projectTaskConfig'),
      model: {
        projectTaskCollection : self.model.projectTaskCollection
      }
    });
    
     window.location.hash = "#";

  },

  _onSaveError : function(model,response) {

    var responseObject=$.parseJSON(response.responseText);
  },

  edit : function(id) {
    var projectTask = this._get(id);

     if (projectTask) {
       this.model.projectTask = projectTask;
       this.render();
     }
  },
  _get : function(id) {
    var projectTask = _.find(this.model.projectTaskCollection.models, function (projectTask) {
       if (projectTask.get('id') == id) {
        return projectTask;
      }
    });
    return projectTask;
  },
  _changed:function(e) {
    var name = $(e.currentTarget).attr('name');
    var val = $(e.currentTarget).val();
    var id = this.model.projectTask.get('id');
    if (id) {
      console.log("Id exists");
       this.model.projectTask.id = id;
    }
    var set = this.model.projectTask.set(name , val, {

    });
  
   },
  close:function () {
    $(this.el).unbind();
    $(this.el).remove();
  }
 

});