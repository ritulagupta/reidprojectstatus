var ProjectTaskRouter = Backbone.Router.extend({

  initialize: function(projectTasks) {

    console.log("Router projectTasks ", projectTasks);

    var self = this;
    this.projectTaskCollection = new ProjectTaskCollection(projectTasks);
    this.projectTask = new ProjectTask();;

    console.log("projectTasks collection ", this.projectTaskCollection);

    this.projectTaskListView = new ProjectTaskListView({
      el: $('#projectTaskConfig'),
      model: {
        projectTaskCollection : this.projectTaskCollection
      }
    });
  },

  routes: {
    "" : "list",
    "add" : "add",
    "edit/:name" : "edit",
    "remove/:name" : "remove"
  },

  list: function() {
    console.log("Calling list in router");
    this.projectTaskListView.render();
  },
  add : function() {
    console.log("IN ADD");
    
    var self = this;
    var editview = new EditProjectTaskView({model: {
                                          projectTask :  new ProjectTask(),
                                          projectTaskCollection : self.projectTaskListView.model.projectTaskCollection
                                        }
                                       });
     editview.render();

  },
  edit: function(id) {
    console.log(" IN ROUTER EDIT ", id, this);
    var self = this;
    var editview = new EditProjectTaskView({model: {
                                          projectTask : self.projectTask,
                                          projectTaskCollection : self.projectTaskListView.model.projectTaskCollection
                                        }
                                        });
    editview.edit(id);
  },
  remove : function(id) {
    console.log("ROUTER REMOVE " +id)
    this.projectTaskListView.remove(id);
  }


});




