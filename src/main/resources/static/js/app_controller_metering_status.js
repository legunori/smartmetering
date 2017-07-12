'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MeteringStatusController', ['meteringStatusRest', 'listPaging', 'state', '$scope',
    function(meteringStatusRest, listPaging, state, $scope) {
        var ctrl = this;

        var stateParam = state.param;

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            return meteringStatusRest.getMeteringStatusList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                   id, recDate, recDateFormatted, title, description
                 } */
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {buildCode: '', status: ''};

        //検索パラメーター初期化関数の設定
        ctrl.clear = function () {
            ctrl.param = angular.copy(ctrl.paramInit);
            if (stateParam)
                ctrl.param = stateParam;

            listPaging.refresh();
        };

        //検索パラメーター初期化と検索
        ctrl.clear();

    }]);

