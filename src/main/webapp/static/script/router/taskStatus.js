var TaskStatusRouter = Backbone.Router.extend({

  initialize: function(tasksStatus) {

    console.log("Router tasksStatus ", tasksStatus);

    var self = this;
    this.taskStatusCollection = new TaskStatusCollection(tasksStatus);
    this.taskStatus = new TaskStatus();;

    console.log("taskStatusCollection collection ", this.taskStatusCollection);

    this.taskStatusListView = new TaskStatusListView({
      el: $('#taskStatusConfig'),
      model: {
        taskStatusCollection : this.taskStatusCollection
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
    this.taskStatusListView.render();
  },
  add : function() {
    console.log("IN ADD");
    
    var self = this;
    var editview = new EditTaskStatusView({model: {
                                          taskStatus :  new TaskStatus(),
                                          taskStatusCollection : self.taskStatusListView.model.taskStatusCollection
                                        }
                                       });
     editview.render();
  },
  edit: function(id) {
    console.log(" IN ROUTER EDIT ", id, this);
    var self = this;
    console.log("tsk status edit ", self.taskStatus);
    var editview = new EditTaskStatusView({model: {
                                          taskStatus : self.taskStatus,
                                          taskStatusCollection : self.taskStatusListView.model.taskStatusCollection
                                        }
                                        });
    editview.edit(id);
  },
  remove : function(id) {
    console.log("ROUTER REMOVE " +id)
    this.taskStatusListView.remove(id);
  }


});




