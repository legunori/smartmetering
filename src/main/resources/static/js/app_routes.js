'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('routeApp').config(['$routeProvider', function($routeProvider) {
    $routeProvider
        //ダッシュボード
        .when('/dashboard',{
            controller: 'DashboardController as ctrl',
            templateUrl: 'dashboard'
        })
        //検針状況
        .when('/meteringStatus',{
            controller: 'MeteringStatusController as ctrl',
            templateUrl: 'meteringStatus'
        })
        //検針ライブデータ - SmartEnergy
        .when('/meteringLiveDataSmartenergy',{
            controller: 'MeteringLiveDataSmartenergyController as ctrl',
            templateUrl: 'meteringLiveDataSmartenergy'
        })
        //検針ライブデータ - T&D
        .when('/meteringLiveDataTandd',{
            controller: 'MeteringLiveDataTanddController as ctrl',
            templateUrl: 'meteringLiveDataTandd'
        })
        //検針結果 - 日次集計
        .when('/meterResultDaily',{
            controller: 'MeterResultDailyController as ctrl',
            templateUrl: 'meterResultDaily'
        })
        //検針結果 - 月次集計
        .when('/meterResultMonthly',{
            controller: 'MeterResultMonthlyController as ctrl',
            templateUrl: 'meterResultMonthly'
        })
        .otherwise({
            redirectTo: '/'
        })
}]);