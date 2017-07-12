'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MeterResultMonthlyController', ['meterResultMonthlyRest', 'listPaging', 'state', '$scope',
    function(meterResultMonthlyRest, listPaging, state, $scope) {
        var ctrl = this;

        var editModal = $('#meterValueMonthly-edit-modal');

        var actionDatePicker = $('#actionDate');

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            return meterResultMonthlyRest.getMeterResultMonthlyList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                   id, actionDate, actionDateFormatted, title, description
                 } */
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {buildCode: '', roomCode: '', month:''};

        //検索パラメーター初期化関数の設定
        ctrl.clear = function () {
            ctrl.param = angular.copy(ctrl.paramInit);
            listPaging.refresh();
        };

        //検索パラメーター初期化と検索
        ctrl.clear();

        //メインビュー
        ctrl.view = {
                onClickEditButton: function(rec) {
                    ctrl.editModal.title = '更新';
                    ctrl.editModal.model = {
                        buildingCode: rec.buildingCode,
                        buildingName: rec.buildingName,
                        roomCode: rec.roomCode,
                        month: rec.month,
                        valueEp: rec.valueEp,
                        valueWt: rec.valueWt,
                        valueHw: rec.valueHw,
                        valueCh: rec.valueCh,
                        valueOl: rec.valueOl,
                        valueGs: rec.valueGs,
                        valuePw: rec.valuePw,
                        valuePh: rec.valuePh,

                        delete: false
                    };
                    ctrl.editModal.show();
                },

                onClickAddButton: function() {
                    ctrl.editModal.title = '登録';
                    ctrl.editModal.show();
                }

        };

        //モーダル
        ctrl.editModal = {
            title: '',
            okButtonName: '',
            modelInit: {
                buildingCode:'',
                buildingName:'',
                roomCode:'',
                month:'',
                valueEp:'',
                valueWt:'',
                valueHw:'',
                valueCh:'',
                valueOl:'',
                valueGs:'',
                valuePw:'',
                valuePh:'',
                delete: false
            },
            model: angular.copy(this.modelInit),
            show: function() {
                editModal.modal('show');
                editModal.on('hidden.bs.modal', function () {
                    ctrl.editModal.model = angular.copy(ctrl.editModal.modelInit);
                    $scope.meterValueInputForm.$setPristine()
                });
            },
            /**
             * 更新ボタンクリック
             */
            onClickOk: function(form) {
                //登録・更新
              meterResultMonthlyRest.saveMeterResultMonthly(ctrl.editModal.model, function(res){
                    listPaging.refresh();
                    editModal.modal('hide');
                })
            }
        };

    }]);

