var ProjectTask = Backbone.Model.extend({
    defaults: {
        id: "",
        taskName: ""
    },
    idAttribute: "id",
    initialize: function () {
      this.on("invalid", function (model, error) {
     });
    },
    constructor: function (attributes, options) {
     Backbone.Model.apply(this, arguments);
    },
    validate: function (attr) {
      if (!attr.taskName) {
        return "Invalid task name supplied."
      }
    },
    toString : function() {
      return "projectTask"
    },
     urlRoot: 'api/projectTask'
});


var ProjectTaskCollection = Backbone.Collection.extend({
    model: ProjectTask,
    url: 'api/projectTask'
})