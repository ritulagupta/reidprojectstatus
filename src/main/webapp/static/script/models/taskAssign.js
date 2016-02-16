var TaskStatus = Backbone.Model.extend({
    defaults: {
        id: "",
      
        employeeId : "",
        taskStatus : "",
    },
    relations :[
        {
            type: Backbone.HasOne,
            key: 'id',
            relatedModel: 'Building',
            autoFetch: {
                success: function( model, response ) {
                    //...
                },
                error: function( model, response ) {
                    //...
                }
            }
       },
        {
            type: Backbone.HasOne,
            key: 'id',
            relatedModel: 'ProjectTask',
            autoFetch: {
                success: function( model, response ) {
                    //...
                },
                error: function( model, response ) {
                    //...
                }
            }
       },
       {
            type: Backbone.HasOne,
            key: 'id',
            relatedModel: 'Employee',
            autoFetch: {
                success: function( model, response ) {
                    //...
                },
                error: function( model, response ) {
                    //...
                }
            }
       }
    ],
    
    idAttribute: "id",
    initialize: function () {
        console.log('task Status has been initialized');
        this.on("invalid", function (model, error) {
            console.log("Houston, we have a problem: " + error)
        });
    },
    constructor: function (attributes, options) {
        console.log('Building\'s constructor had been called');
        Backbone.Model.apply(this, arguments);
    },
  
    urlRoot : 'api/taskStatus'
});


var TaskStatusCollection = Backbone.Collection.extend({
    model: TaskStatus,
    url: 'api/taskStatus'
})