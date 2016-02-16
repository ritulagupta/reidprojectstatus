var BuildingRouter = Backbone.Router.extend({

  initialize: function(buildings) {

    console.log("Router buildings ", buildings);

    var self = this;
    this.buildingCollection = new BuildingCollection(buildings);
    this.building = new Building();;

    console.log("Bulding collection ", this.buildingCollection);

    this.buildingListView = new BuildingListView({
      el: $('#buildingConfig'),
      model: {
        buildingCollection : this.buildingCollection
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
    this.buildingListView.render();
  },
  add : function() {
    console.log("IN ADD");
    
    var self = this;
    var editview = new EditBuildingView({model: {
                                          building :  new Building(),
                                          buildingCollection : self.buildingListView.model.buildingCollection
                                        }
                                       });
     editview.render();

  },
  edit: function(id) {
    console.log(" IN ROUTER EDIT ", id, this);
    var self = this;
    var editview = new EditBuildingView({model: {
                                          building : self.building,
                                          buildingCollection : self.buildingListView.model.buildingCollection
                                        }
                                        });
    editview.edit(id);
  },
  remove : function(id) {
    console.log("ROUTER REMOVE " +id)
    this.buildingListView.remove(id);
  }


});




