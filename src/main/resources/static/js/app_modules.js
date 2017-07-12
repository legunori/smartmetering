'use strict';
/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/08.
 */
angular.module('routeApp',['ngRoute','ngAnimate']);
angular.module('restApp',['ngResource']);
angular.module('modelApp', []);
angular.module('utils', []);
angular.module('mainApp',['routeApp','restApp','modelApp','ngStorage','ngSanitize', 'ui.sortable', 'utils']);

/**
 * ※文字列からhtmlタグを取り除く関数
 * フリーワード検索によって取得したデータには、検索ワードと一致する部分にサーバーサイドでHTMLタグが挿入されるので
 * 入力項目で編集させる必要がある場合に使用する
 */
function clearTag(str) {return $('<div>').html(str).text()}

