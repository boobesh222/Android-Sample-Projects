// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
var imageApp = angular.module("imageApp", ["ionic", "ngCordova","firebase"]);

 
var config = {
    apiKey: "AIzaSyDh67Ad2Xz9ZsDXy_CiAHZ6VZwcU91O_lM",
    authDomain: "ioniccameraapp.firebaseapp.com",
    databaseURL: "https://ioniccameraapp.firebaseio.com",
    storageBucket: "ioniccameraapp.appspot.com",
  };
firebase.initializeApp(config);
//var fb = new Firebase("https://ioniccameraapp.firebaseio.com/");


imageApp.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

//State Configuration
imageApp.config(function($stateProvider,$urlRouterProvider){
$stateProvider
  .state("firebase",{
    url: "/firebase",
            templateUrl: "templates/firebase.html",
            controller: "FirebaseController",
            cache: false
  })
  .state("secure",{
    url: "/secure",
            templateUrl: "templates/secure.html",
            controller: "SecureController",
           
  });
$urlRouterProvider.otherwise('/firebase');
});

imageApp.controller("FirebaseController", function($scope, $state, $firebaseAuth) {

    var fbAuth = firebase.auth();

    $scope.login = function(username, password) {
        fbAuth.signInWithEmailAndPassword(username,password).then(function(authData) {
          alert(authData);
            $state.go("secure");
        }).catch(function(error) {
            console.error("ERROR: " + error);
        });
    }

    $scope.register = function(username, password) {
        fbAuth.createUserWithEmailAndPassword(username,password).then(function(userData) {
            return fbAuth.signInWithEmailAndPassword(username,password);
        }).then(function(authData) {
            alert(authData);
            $state.go("secure");
        }).catch(function(error) {
            console.error("ERROR: " + error);
        });
    }

});

imageApp.controller("SecureController", function($scope, $ionicHistory, $firebaseArray, $cordovaCamera) {

    $ionicHistory.clearHistory();

    $scope.images = [];

    var fbAuth = new firebase.auth.EmailAuthProvider();
    if(fbAuth) {
        var userReference = firebase.child("users/" + fbAuth.uid);
        var syncArray = $firebaseArray(userReference.child("images"));
        $scope.images = syncArray;
    } else {
        $state.go("firebase");
    }

    $scope.upload = function() {
        var options = {
            quality : 75,
            destinationType : Camera.DestinationType.DATA_URL,
            sourceType : Camera.PictureSourceType.CAMERA,
            allowEdit : true,
            encodingType: Camera.EncodingType.JPEG,
            popoverOptions: CameraPopoverOptions,
            targetWidth: 500,
            targetHeight: 500,
            saveToPhotoAlbum: false
        };
        $cordovaCamera.getPicture(options).then(function(imageData) {
            syncArray.$add({image: imageData}).then(function() {
                alert("Image has been uploaded");
            });
        }, function(error) {
            console.error(error);
        });
    }

});


