'use strict';

/**
 * @ngdoc service
 * @name app.app.config
 * @description
 * # app.config
 * Constant in the app.
 */
// const serverUrl = 'http://153.122.77.121:8080'; //開発環境 (旧UAT)
// const serverUrl = 'http://153.122.80.76:8080'; //本番環境(専用サーバー)
// const serverUrl = 'http://153.122.80.241:8080'; //新UAT
// const serverUrl = 'http://153.122.76.193:8080'; //デモ
const serverUrl = 'http://localhost:8080';

angular.module('mainApp')
  .constant('appConfig', {
    toWarekiYear: function (y) {
      let s;
      if (y > 1988) {
        s = '平成'+(y-1988)+'年';
      } else if (y > 1925) {
        s = '昭和'+(y-1925)+'年';
      } else if (y > 1911) {
        s = '大正'+(y-1911)+'年';
      } else if (y > 1867) {
        s = '明治'+(y-1867)+'年';
      }
      return s;
    },
    toSeirekiYear: function (gy) {
      let g = gy.charAt(0).toUpperCase();
      let y = undefined;
      try {
        y = parseInt(gy.substr(1));
      } catch (e) {
        return '';
      }

      let yyyy = '';
      if(g == 'H') {
        yyyy =  1988 + y;
      } else if(g == 'S') {
        yyyy =  1925 + y;
      } else if(g == 'T') {
        yyyy =  1911 + y;
      } else if(g == 'M') {
        yyyy =  1867 + y;
      }
      return yyyy;
    }
});
