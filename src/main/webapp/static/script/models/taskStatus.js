var TaskStatus = Backbone.Model.extend({
    defaults: {
      id: "",
      projectTaskId : "",
      taskName : "",
      buildingId : "",
      buildingName : "",
      employeeId : "",
      firstName : "",
      statusType:""
    },
    idAttribute: "id",
    initialize: function () {
      console.log('task Status has been initialized');
      this.on("invalid", function (model, error) {
         console.log("Error while initialising taskStatus : " + error)
      });
    },
    constructor: function (attributes, options) {
      Backbone.Model.apply(this, arguments);
    },
   
    urlRoot : 'api/taskStatus'
});


var TaskStatusCollection = Backbone.Collection.extend({
    model: TaskStatus,
    url: 'api/taskStatus',
    findByEmployeeId: function(id){
        var employee = this.models.where({employeeId: id});
   },
   fetchByEmployee: function (employeeId, options) {
        options = options || {};
        if (options.url === undefined) {
          options.url = this.url + "/employee/" + employeeId ;
        }
        options.success = this.postSuccess;
        Backbone.Model.prototype.fetch.call(this, options);
    },
    postSuccess : function(data) {
       // self.set(data);
        console.log("fetch successfully");
    },

});

 var EmployeeTaskStatusCollection = TaskStatusCollection.extend({
    url: 'api/taskStatus/employee',
  });
