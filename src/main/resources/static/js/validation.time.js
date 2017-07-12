'use strict';

/**
 * @ngdoc directive
 * @name app.directive:validationTime
 * @description
 * # validationTime
 */
angular.module('app')
  .directive('validationTime', function () {
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function(scope, element, attrs, ngModel) {
        ngModel.$validators.hhmm = function(modelValue, viewValue) {
          let value = modelValue || viewValue;
          return /^(\d+):(\d+)$/.test(value);
        };
        ngModel.$validators.hour = function(modelValue, viewValue) {
          let value = modelValue || viewValue;
          if (value) {
            let matches = value.match(/^(\d{1,2}):\d+$/);
            if (matches) {
              let hour = Number(matches[1]);
              return (0 <= hour && hour <= 23);
            }
          }
          return false;
        };
        ngModel.$validators.minute = function(modelValue, viewValue) {
          let value = modelValue || viewValue;
          if (value) {
            let matches = value.match(/^\d+:(\d{2})$/);
            if (matches) {
              let minute = Number(matches[1]);
              return (0 <= minute && minute <= 59);
            }
          }
          return false;
        };
      }
    };
  });
