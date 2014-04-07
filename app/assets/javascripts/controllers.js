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
    $scope.logs = []

    function addMsg(msg) {
        var json = JSON.parse(msg.data)
        json.forEach(function(entry) {
            $scope.logs.push(entry)
        })
        $scope.$apply()
    }

    var logFeed = new EventSource("/logs/feed")
    logFeed.addEventListener("message", addMsg, false)
  })
