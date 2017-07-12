'use strict';

/**
 * @ngdoc directive
 * @name app.directive:validations
 * @description
 * # validations
 * カスタムバリデーション
 */
angular.module('mainApp')
  //モデルを比較するバリデーション
  .directive('equalsTo', ['$parse', function ($parse) {
    return {
      require: 'ngModel',
      link: function(scope, elem, attrs, ctrl) {
        scope.$watch(function() {
          var target = $parse(attrs.equalsTo)(scope);  // 比較対象となるモデルの値
          return !ctrl.$modelValue || target === ctrl.$modelValue;
        }, function(currentValue) {
          ctrl.$setValidity('mismatch', currentValue);
        });
      }
    }
  }])
  //日付の妥当性をチェックするバリデーション
  .directive('dateCheck', ['appConfig', function (appConfig) {
    let y, m, d;
    return {
      require: 'ngModel',
      link: function(scope, elem, attrs, ctrl) {
        scope.$watch(function() {
          if(!ctrl.$modelValue) return null;
          let ymd = ctrl.$modelValue.split(/[/\-]/);

          if(ymd.length === 2 && ymd[0].length<3 && ymd[1].length<3 && ymd[0].length>0 && ymd[1].length>0) {
            //月日入力時(m/d)
            let now = new Date();
            y = now.getFullYear();
            m = ymd[0];
            d = ymd[1];
          } else if(ymd.length === 1 && ymd[0].length===4) {
              //月日入力時(mmdd)
              let now = new Date();
              y = now.getFullYear();
              m = ymd[0].substr(0, 2);
              d = ymd[0].substr(2);
          } else if(ymd.length > 0 && ymd[0].length==3) {
            //和暦年入力時(gyy)
            let s = appConfig.toSeirekiYear(ymd[0]);
            if(s) {
              ctrl.$setViewValue(s);
              ctrl.$render();
              return false;
            }
            return false;
          } else if(ymd.length > 0 && ymd[0].length>=8) {
            y = ymd[0].substring(0, 4);
            m = ymd[0].substring(4, 6);
            d = ymd[0].substring(6, 8);
          } else if(ymd.length>=3) {
            y = parseInt(ymd[0]);
            m = parseInt(ymd[1]);
            d = parseInt(ymd[2]);
          } else {
            return false;
          }
          let dt=new Date(y,m-1,d);
          let val = dt.getFullYear()==y && dt.getMonth()==m-1 && dt.getDate()==d;
          if(val) {
            ctrl.$setViewValue(y + '/' + m + '/' + d);
            ctrl.$render();
          }
          return val;
        }, function(currentValue) {
          ctrl.$setValidity('isDate', currentValue);
        });
      }
    }
  }])
  //時刻の妥当性をチェックするバリデーション
  .directive('timeCheck', ['appConfig', function (appConfig) {
    let h, m;
    return {
      require: 'ngModel',
      link: function(scope, elem, attrs, ctrl) {
        scope.$watch(function() {
          if(!ctrl.$modelValue) return null;
          let hm = ctrl.$modelValue.split(/[/\D\s]/);
          if(hm.length === 2 && hm[0].length<3 && hm[0].length>0 && hm[1].length===2) {
            //時刻入力時(h:m)
            h = hm[0];
            m = hm[1];
          } else if(hm.length === 1 && hm[0].length===4) {
            //時分入力時(hhmm)
            h = hm[0].substr(0, 2);
            m = hm[0].substr(2);
          } else if(hm.length === 2 && hm[0].length===0) {
            ctrl.$setViewValue('');
            ctrl.$render();
            return false;
          } else {
        	return false;
          }
          let val = h.match(/^[0-9]+$/) && m.match(/^[0-9]+$/) && parseInt(h) >=0 && parseInt(h) <= 24 && parseInt(m) >= 0 && parseInt(m) <= 59;

          if(val) {
            ctrl.$setViewValue(h + ':' + m);
            ctrl.$render();
          } else{
              ctrl.$setViewValue('12:00');
              ctrl.$render();
          }
          return val;
        }, function(currentValue) {
          ctrl.$setValidity('isTime', currentValue);
        });
      }
    }
  }]);
