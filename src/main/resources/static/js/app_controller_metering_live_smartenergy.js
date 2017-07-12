'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MeteringLiveDataSmartenergyController', ['meteringLiveDataRest', 'listPaging', 'state', '$scope',
    function(meteringLiveDataRest, listPaging, state, $scope) {
        var ctrl = this;

        var recDatePicker = $('#recDate');

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            return meteringLiveDataRest.getMeteringLiveDataSmartenergyList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                   id, recDate, recDateFormatted, title, description
                 } */
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {buildCode: '', roomCode: '', recDate: getDate()};

        //検索パラメーター初期化関数の設定
        ctrl.clear = function () {
            ctrl.param = angular.copy(ctrl.paramInit);

            listPaging.refresh();
        };

        //検索パラメーター初期化と検索
        ctrl.clear();

    }]);

function getDate(){
    var date = new Date();
    var year = date.getYear();
    var year4 = (year < 2000) ? year+1900 : year;
    var month = date.getMonth() + 1;
    var date = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (date < 10) {
        date = "0" + date;
    }
    var strDate = year4 + "/" + month + "/" + date;
    return strDate;
}

