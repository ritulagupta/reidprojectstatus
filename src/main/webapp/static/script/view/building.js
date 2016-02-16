var buildings = {};


var BuildingListView = Backbone.View.extend ({

    el : '#buildingConfig',// The element we defined in HTML
    initialize : function() {
      console.log("this.model ", this.model);
      var self = this;
      this.model.buildingCollection.fetch({
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
        var editing = this.editedBuilding !== null;
        this.editedBuildingView=null;

        var self = this;// Saving the scope object
      
        var buildingList = this.model.buildingCollection.models;
    
        var buildingTemplate = _.template($('#buildingListTemplate').html(), { buildings: buildingList });
       
   
         _.each(buildingList, function (building) {
        
          if (self.editedBuilding === building) {
   
            var editview = new EditBuildingView({
              model: {
                building : building,
                buildingList : self.model.buildingCollection
              }
            });

            self.editedBuildingView = editview;
            editview.render();
       } 
        });
         this.$el.html(buildingTemplate);
        return this;
      },
      add : function() {
        var building = new Building();
        console.log("this ", this.model);
        this.model.buildingCollection.add(building);
        this.editedBuilding = building;
        this.render();
      },
      edit : function(id) {
        var building = this._get(id);
        if (building) {
          this.building = building;
          this.render();
        }
        // this.editedBuilding = building;
        // this.render();
      },
      _get : function(id) {
        var building = _.find(this.model.buildingCollection.models, function (building) {
          if (building.get('id') == id) {
            return building;
          }
        });
        return building;
      },

      remove: function(id) {
        var building = this._get(id);
  
        if (building) {
          var self = this;
          building.destroy({
            wait: true,
            success: function () {
              console.log("destroy building");
              self.render();
            },
            error: function(model,response) {
              if (response) {
                $("#errorbox").html(response.statusText);
              }
            }
          });
        }
      },
      _confirmDeleteBuilding : function(e) {
        var url = $(e.currentTarget).data('url');
        var id = $(e.currentTarget).data('id');
        window.location.href = url;
        return false;
      },

      confirmDelete : function(url, name) {
        $("#confirmDelete").dialog({
          modal: true,
          bgiframe: true,
          autoOpen: false });

        $('#confirmDelete').html("Are you sure you want to delete " + name + " ?" );
        $('#confirmDelete').dialog('option', 'buttons', { 
          "Yes": function() { 
            $(this).dialog("close");
            window.location.href = url;
          },
          "No": function() { $(this).dialog("close"); } });
           $('#confirmDelete').dialog('open');

      }    
});


var EditBuildingView = Backbone.View.extend({

    el : '#buildingConfig',

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

     this.model.building.bind("destroy", this.close, this);
      
    },

    render: function() {
      console.log("IN RENDER", this.model.building);
      var buildingTemplate = _.template($('#editBuildingTemplate').html(), 
                               { building : this.model.building ,
                                buildingList : this.model.buildingList});
      this.$el.html(buildingTemplate);
      return this;
    },
    _save : function() {
      var method ='POST';
      if(this.model.building.id) {
        method='PUT';
      }
      this.model.building.save({},{
         type : method,
         success : this._onSaveSuccess,
         error : this._onSaveError
       });
    },
    _onSaveSuccess : function(model,response) {
      var self = this;
      this.buildingListView = new BuildingListView({
        el: $('#buildingConfig'),
        model: {
          buildingCollection : self.model.buildingCollection
        }
      });
      
       window.location.hash = "#";
  
    },
    _get : function(id) {

      var building = _.find(this.model.buildingCollection.models, function (building) {
        if (building.get('id') == id) {
          return building;
        }
      });
      return building;
    },

    _onSaveError : function(model,response) {

      if(response) {  
        $("#errorbox").html(response.statusText)
      } 
    },

    edit : function(id) {
      var building = this._get(id);
  
       if (building) {
         this.model.building = building;
         this.render();
       }
    },
    _get : function(id) {
      var building = _.find(this.model.buildingCollection.models, function (building) {
         if (building.get('id') == id) {
          return building;
        }
      });
      return building;
    },
    _changed:function(e) {
      var name = $(e.currentTarget).attr('name');
      var val = $(e.currentTarget).val();
      var id = this.model.building.get('id');
      if (id) {
        console.log("Id exists");
         this.model.building.id = id;
      }
      var set = this.model.building.set(name , val, {

      });
  
   },
  close:function () {
      $(this.el).unbind();
      $(this.el).remove();
    }
 

  });