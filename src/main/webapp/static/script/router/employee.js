var EmployeeRouter = Backbone.Router.extend({

  initialize: function(employees) {

    console.log("Router employees ", employees);

    var self = this;
    this.employeeCollection = new EmployeeCollection(employees);
    this.employee = new Employee();;

    console.log("Employee collection ", this.employeeCollection);

    this.employeeListView = new EmployeeListView({
      el: $('#employeeConfig'),
      model: {
        employeeCollection : this.employeeCollection
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
    this.employeeListView.render();
  },
  add : function() {
    console.log("IN ADD");
    
    var self = this;
    var editview = new EditEmployeeView({model: {
                                          employee :  new Employee(),
                                          employeeCollection : self.employeeListView.model.employeeCollection
                                        }
                                       });
     editview.render();

  },
  edit: function(id) {
    console.log(" IN ROUTER EDIT ", id, this);
    var self = this;
    var editview = new EditEmployeeView({model: {
                                          employee : self.employee,
                                          employeeCollection : self.employeeListView.model.employeeCollection
                                        }
                                        });
    editview.edit(id);
  },
  remove : function(id) {
    console.log("ROUTER REMOVE " +id)
    this.employeeListView.remove(id);
  }


});




