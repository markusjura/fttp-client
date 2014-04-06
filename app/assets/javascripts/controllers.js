'use strict';

/** Controllers */
angular.module('log.controllers', [])
  .filter('logLevel', function() {
    return function(logs, logLevel) {
      var filteredLogs = [];
      for (var i=0; i < logs.length; i++){
        if (logs[i].level == logLevel) {
            filteredLogs.push(logs[i]);
        }
      }

      return filteredLogs;
    }
  })

  .controller('LogsCtrl', function($scope) {
    // initialize log messages
    $scope.logs = []

    // logLevel filter
    $scope.filters = {}

    $scope.addMsg = function(msg) {
      $scope.$apply(function() {
        var json = JSON.parse(msg.data)
        json.forEach(function(entry) {
          $scope.logs.push(entry)
        })
      })
    }

    $scope.logFeed = new EventSource("/logFeed")
    $scope.logFeed.addEventListener("message", $scope.addMsg, false)
  })
