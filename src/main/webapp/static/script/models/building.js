var Building = Backbone.Model.extend({
    defaults: {
        id: "",
        buildingName: "",
        address : ""
    },
    idAttribute: "id",
    initialize: function () {
        console.log('Building has been initialized');
        this.on("invalid", function (model, error) {
            console.log(" we have a problem: " + error)
        });
    },
    constructor: function (attributes, options) {
        console.log('Building\'s constructor had been called');
        Backbone.Model.apply(this, arguments);
    },
    validate: function (attr) {
        if (!attr.buildingName) {
            return "Invalid building name supplied."
        }
    },
    urlRoot : 'api/building'
});


var BuildingCollection = Backbone.Collection.extend({
    model: Building,
    url: 'api/building'
})