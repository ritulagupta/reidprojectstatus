var employees = {};


var EmployeeListView = Backbone.View.extend ({

  el : '#employeeConfig',// The element we defined in HTML
  initialize : function() {
    console.log("this.model ", this.model);
    var self = this;
    this.model.employeeCollection.fetch({
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
      var editing = this.editedEmployee !== null;
      this.editedEmployeeView=null;

      var self = this;// Saving the scope object
    
      var employeeList = this.model.employeeCollection.models;
  
      var employeeTemplate = _.template($('#employeeListTemplate').html(), { employees: employeeList });
     
 
       _.each(employeeList, function (employee) {
      
        if (self.editedEmployee === employee) {
 
          var editview = new EditEmployeeView({
            model: {
              employee : employee,
              employeeList : self.model.employeeCollection
            }
          });

          self.editedEmployeeView = editview;
          editview.render();
     } 
      });
       this.$el.html(employeeTemplate);
      return this;
    },
   
    _get : function(id) {
      var employee = _.find(this.model.employeeCollection.models, function (employee) {
        if (employee.get('id') == id) {
          return employee;
        }
      });
      return employee;
    },

    remove: function(id) {
      var employee = this._get(id);
    
      if (employee) {
        var self = this;
        employee.destroy({
          wait: true,
          success: function () {
            console.log("destroy employee");
            self.render();
          },
          error: function(model,response) {
            if (response) {
              console.log("Error ", response);
    
              $("#errorbox").html(response.statusText) //get element by ID

            }
          }
        });
      }
    },
    
     
});


var EditEmployeeView = Backbone.View.extend({

  el : '#employeeConfig',

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

   this.model.employee.bind("destroy", this.close, this);
    
  },

  render: function() {
    console.log("IN RENDER", this.model.employee);
    var employeeTemplate = _.template($('#editEmployeeTemplate').html(), 
                             { employee : this.model.employee ,
                              employeeList : this.model.employeeList});
    this.$el.html(employeeTemplate);
    return this;
  },
  _save : function() {
    var method ='POST';
    if(this.model.employee.id) {
      method='PUT';
    }
    this.model.employee.save({},{
       type : method,
       success : this._onSaveSuccess,
       error : this._onSaveError
     });
  },
  _onSaveSuccess : function(model,response) {
    var self = this;
    this.employeeListView = new EmployeeListView({
      el: $('#employeeConfig'),
      model: {
        employeeCollection : self.model.employeeCollection
      }
    });
    
     window.location.hash = "#";

  },
  _get : function(id) {

    var employee = _.find(this.model.employeeCollection.models, function (employee) {
      if (employee.get('id') == id) {
        return employee;
      }
    });
    return employee;
  },

  _onSaveError : function(model,response) {
    if(response) {  
      $("#errorbox").html(response.statusText);
    } 
  },

  edit : function(id) {
    var employee = this._get(id);

     if (employee) {
       this.model.employee = employee;
       this.render();
     }
  },
  _get : function(id) {
    var employee = _.find(this.model.employeeCollection.models, function (employee) {
       if (employee.get('id') == id) {
        return employee;
      }
    });
    return employee;
  },
  _changed:function(e) {
    var name = $(e.currentTarget).attr('name');
    var val = $(e.currentTarget).val();
    var id = this.model.employee.get('id');
    if (id) {
      console.log("Id exists");
       this.model.employee.id = id;
    }
    var set = this.model.employee.set(name , val, {

    });

 },
close:function () {
    $(this.el).unbind();
    $(this.el).remove();
  }
 

});