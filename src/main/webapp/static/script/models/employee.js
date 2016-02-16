var Employee = Backbone.Model.extend({
    defaults: {
        id: "",
        firstName: "",
        lastName : "",
        address : ""
    },
    idAttribute: "id",
    initialize: function () {
        this.on("invalid", function (model, error) {
            console.log("we have a problem: " + error)
        });
    },
    constructor: function (attributes, options) {
        Backbone.Model.apply(this, arguments);
    },
    urlRoot : 'api/employee'
});


var EmployeeCollection = Backbone.Collection.extend({
    model: Employee,
    url: 'api/employee'
})