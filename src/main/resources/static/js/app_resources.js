'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/09/08.
 */
var restApp = angular.module('restApp');

/**
 * リクエストパスの記述
 */
restApp.factory('commonRest', ['$resource', function($resource) {
    //共通項目・マスタ
    return $resource('',{},{
        getBuildingList:         {method: 'GET', url: 'common/buildingList', isArray:true},
        getRoomList:             {method: 'GET', url: 'common/roomList', isArray:true},
        getMeteringStatusMasterList: {method: 'GET', url: 'common/meteringStatusMasterList', isArray:true},
        getMeterMonthList:       {method: 'GET', url: 'meterResultMonthly/meterMonthList', isArray:true}
    });
}]).factory('dashboardRest', ['$resource', function($resource) {
    //ダッシュボード
    return $resource('',{},{
        getMeteringStatus:       {method: 'GET', url: 'dashboard/meteringStatus', isArray:true}
    });
}]).factory('meteringStatusRest', ['$resource', function($resource) {
    //検針状況
    return $resource('',{},{
        getMeteringStatusList:   {method: 'GET', url: 'meteringStatus/meteringStatusList', isArray:true}
    });
}]).factory('meteringLiveDataRest', ['$resource', function($resource) {
    //検針ライブデータ
    return $resource('',{},{
        getMeteringLiveDataSmartenergyList:   {method: 'GET', url: 'meteringLiveData/meteringLiveDataSmartenergyList', isArray:true},
        getMeteringLiveDataTanddList:         {method: 'GET', url: 'meteringLiveData/meteringLiveDataTanddList', isArray:true}
       });
}]).factory('meterResultDailyRest', ['$resource', function($resource) {
    //検針結果 日次集計データ
    return $resource('',{},{
        getMeterResultDailyList:    {method: 'GET', url: 'meterResultDaily/meterResultDailyList', isArray:true},
        saveMeterResultDaily:       {method: 'POST', url: 'meterResultDaily/save'},
        deleteMeterResultDailyt:    {method: 'DELETE', url: 'meterResultDaily/delete'},
        meterResultDownload:         {method: 'GET', url: 'meterresult/MeterResultDownload', isArray:true},
        getMeterResultDailyDefaultDate :{method: 'GET', url: 'meterResultDaily/meterResultDailyDefaultDate', isArray:true}
    });
}]).factory('meterResultMonthlyRest', ['$resource', function($resource) {
    //検針結果 月次集計データ
    return $resource('',{},{
        getMeterResultMonthlyList:    {method: 'GET', url: 'meterResultMonthly/meterResultMonthlyList', isArray:true},
        saveMeterResultMonthly:       {method: 'POST', url: 'meterResultMonthly/save'},
    });
}])
;


/**
 * Interceptorにより
 * 共通のヘッダー処理、エラーハンドリングを行う
 */
restApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push(['$q', function($q) {
        return {
            // リクエストの前処理
            request: function(config) {
                console.log(config);
                return config;
            },
            // レスポンス受信時の前処理
            response: function(response) {
                console.log(response);
                return response;
            },
            // 通信エラー時の処理
            responseError: function(rejection) {
                console.log(rejection);
                if (500 == rejection.status) {
                    alert('内部サーバーエラーが発生しました');
                } else {
                    alert("通信エラーが発生しました");
                }
                return $q.reject(rejection);
            }
        };
    }]);
}]);