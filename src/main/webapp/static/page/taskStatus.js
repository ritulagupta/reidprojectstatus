var router = new TaskStatusRouter(bootstrap.tasksStatus);
// router.on('route:add', function () {

//     // Todo: code to render create page
//     console.log("Display Create Page");
// });

// When hash tag has localhost#create register the below route
// router.on('route:create', function () {

//     // Todo: code to render create page
//     console.log("Display Create Page");
// });

// //When hash tag has localhost#edit/1 register the below route
// router.on('route:edit', function () {

//     // Todo: code to render edit page and render user details
//     console.log("Display Edit Page");
// });
Backbone.history.start({root:bootstrap.pageRoot});
