'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MeterResultDailyController', ['meterResultDailyRest', 'listPaging', 'state', '$scope',
    function(meterResultDailyRest, listPaging, state, $scope) {
        var ctrl = this;

        var editModal = $('#meterValueDaily-edit-modal');

        var recDatePicker = $('#fromDate');

        var defaultFromDate;
        var defaultToDate;

        //検索デフォルト日付
        var getMeterResultDailyDefaultDate = function(){
            meterResultDailyRest.getMeterResultDailyDefaultDate(function(res) {
            	ctrl.param.fromDate=res.fromDate;
            	ctrl.param.toDate=res.toDate;
            }).$promise;;
        };

//         ctrl.defaultCondi();

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            return meterResultDailyRest.getMeterResultDailyList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                   id, recDate, recDateFormatted, title, description
                 } */
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        var today= new Date();
        var jikan = ('0' + today.getHours()).slice(-2) + ":" + ('0' + today.getMinutes()).slice(-2);

        var fromDate;
        var toDate;

        var fromYear;
        var fromMonth;
        var toYear;
        var toMonth;

        if (today.getDate() >=21){
        	// 21日以降は当月21日～翌月20日
        	if ((today.getMonth() + 1) < 10) {
             fromMonth = "0" + (today.getMonth() + 1);
           }else{
             fromMonth = (today.getMonth() + 1);
           }

          if ((today.getMonth() + 2) < 10) {
        	  toYear = today.getFullYear();
        	  toMonth = "0" + (today.getMonth() + 2);
          }else if(today.getMonth() > 10){
        	//当月が12月の場合は翌年かつ1月
            toYear = today.getFullYear() + 1;
            toMonth = "01";
          } else{
            toYear = today.getFullYear();
            toMonth = (today.getMonth() + 2);
          }
          fromDate = today.getFullYear() + "/" + fromMonth + "/21";
          toDate = toYear + "/" + toMonth + "/20";
        }else{
        	// 21日以前は前月21日～当月20日
           if (today.getMonth() == 0) {
        	   // 当月が1月の場合は、前年12月
        	   fromYear = today.getFullYear() - 1;
        	   fromMonth = 12;
           }else if (today.getMonth() < 10) {
             fromMonth = "0" + today.getMonth();
           }else{
             fromMonth = today.getMonth();
           }
           if ((today.getMonth() + 1) < 10) {
        	   toYear = today.getFullYear();
        	   toMonth = "0" + (today.getMonth() + 1);
           }else if(today.getMonth() > 10){
              toYear = today.getFullYear() + 1;
              toMonth = "01";
          }else{
            toYear = today.getFullYear();
            toMonth = today.getMonth() + 1;
          }
          fromDate = fromYear + "/" + fromMonth + "/21";
          toDate = toYear + "/" + toMonth + "/20";
        }

        //検索パラメーター
//        ctrl.paramInit = {buildCode: '', roomCode: '', fromDate: '2017/05/21', toDate: '2017/06/20', toTime: jikan};
        ctrl.paramInit = {buildCode: '', roomCode: '', fromDate: fromDate, toDate: toDate, toTime: jikan};

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
                    recDate: rec.recDate,
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
                recDatePicker.datepicker('update', rec.recDate);
            },

            onClickAddButton: function() {
                ctrl.editModal.title = '登録';
                ctrl.editModal.show();
//                ctrl.editModal.model.actionDate='1900/01/01';
                recDatePicker.datepicker('update', '');
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
                recDate:'',
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
              meterResultDailyRest.saveMeterResultDaily(ctrl.editModal.model, function(res){
                    listPaging.refresh();
                    editModal.modal('hide');
                })
            }
        };

    }]);

