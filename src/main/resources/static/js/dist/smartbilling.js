'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('BillingItemMasterController', ['billingItemMasterRest', 'listPaging', 'state', '$scope',
    function(billingItemMasterRest, listPaging, state, $scope) {
        var ctrl = this;

//         ctrl.param = {words: '', fromDate:'', toDate:''};
//         listPaging.triggerFunction = function() {
//             listPaging.setLoading(true);
//             return eventCalendarRest.getEventCalendarList(listPaging.mergeParam(ctrl.param), function(res) {
//                 /**
//                  id
//                  actionDate
//                  actionDateFormatted
//                  title
//                  description
//                  */
//
//                 listPaging.setData(res);
//                 listPaging.setLoading(false);
//             });
//         };
//
//         //メインビュー
//         ctrl.view = {
//
//             onClickEditButton: function(rec) {
//                 ctrl.editModal.title = '更新';
//                 ctrl.editModal.model = {
//                     id: rec.id,
//                     status: rec.status,
//                     actionDate: rec.actionDate,
//                     title: clearTag(rec.title), //ハイライトタグを取り除く
//                     description: clearTag(rec.description), //ハイライトタグを取り除く
//                     delete: false
//                 };
//                 ctrl.editModal.show();
//                 $('#actionDate').datepicker('update', rec.actionDate);
//             },
//
//             onClickAddButton: function() {
//                 ctrl.editModal.title = '登録';
//                 ctrl.editModal.show();
// //                ctrl.editModal.model.actionDate='1900/01/01';
//                 $('#actionDate').datepicker('update', '');
//             }
//
//         };
//
//         //モーダル
//         ctrl.editModal = {
//             title: '',
//             okButtonName: '',
//             modelInit: {
//                 id:'',
//                 status: '',
//                 actionDate:'',
//                 title:'',
//                 description: '',
//                 delete: false
//             },
//             model: angular.copy(this.modelInit),
//             show: function() {
//                 $('#event-edit-modal').modal('show');
//                 $('#event-edit-modal').on('hidden.bs.modal', function () {
//                     ctrl.editModal.model = angular.copy(ctrl.editModal.modelInit);
//                     $scope.eventInputForm.$setPristine()
//                 });
//             },
//             /**
//              * 更新ボタンクリック
//              */
//             onClickOk: function(form) {
//                 if(!ctrl.editModal.model.delete && form.$invalid) return;
//
//                 if(ctrl.editModal.model.delete) {
//                     //削除
//                     eventCalendarRest.deleteEventCalendar({id: ctrl.editModal.model.id}, function(res){
//                         listPaging.refresh();
//                         $('#event-edit-modal').modal('hide');
//                     })
//                 } else {
//                     //登録・更新
//                     eventCalendarRest.saveEventCalendar(ctrl.editModal.model, function(res){
//                         listPaging.refresh();
//                         $('#event-edit-modal').modal('hide');
//                     })
//                 }
//             }
//         };

        listPaging.refresh();
    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('ContractListController', ['contractRest', 'listPaging', 'state', '$scope',
    function(contractRest, listPaging, state, $scope) {
        var ctrl = this;

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            //物件の契約情報一覧を取得
            return contractRest.getLeaseholdList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                 contractId        ・・・契約ID（キー）
                 customerName      ・・・契約者名
                 customerType      ・・・1:個人 2:法人
                 type              ・・・物件タイプ
                 buildingCode      ・・・建物コード　※名称は保持しているMapから取得
                 propertyName      ・・・物件名称：部屋名称（番号）／その他物件名称
                 contractStart     ・・・契約開始日
                 contractFrom      ・・・現契約開始日
                 contractTo        ・・・現契約満期日
                 contractEnd       ・・・解約（予定）日
                 status            ・・・契約ステータス
                 } */

                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {words: '', buildingCode:'', customerType:'', status:''};

        //検索パラメーター初期化関数の設定
        ctrl.clear = function() {
            ctrl.param = angular.copy(ctrl.paramInit);
            listPaging.refresh();
        };

        //検索パラメータの初期化と検索
        ctrl.clear();

    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('ContractNewController', ['contractRest', 'state', '$scope',
    function(contractRest, state, $scope) {
        var ctrl = this;
    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('CustomerListController', ['customerRest', 'listPaging', 'state', '$scope',
    function(customerRest, listPaging, state, $scope) {
        var ctrl = this;

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            //物件の契約情報一覧を取得
            return customerRest.getCustomerList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                customerId;        //・・・顧客ID（キー）
                customerCode;      //・・・顧客コード
                customerType;      //・・・1:個人 2:法人
                customerName;      //・・・顧客名
                address;           //・・・顧客住所
                contactName;       //・・・担当者名
                contactDept;       //・・・担当部署
                paymentMethod;     //・・・支払方法
               } */

                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {words: '', customerType:'', paymentMethod:''};

        //検索パラメーター初期化関数の設定
        ctrl.clear = function() {
            ctrl.param = angular.copy(ctrl.paramInit);
            listPaging.refresh();
        };

        //検索パラメータの初期化と検索
        ctrl.clear();

    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('CustomerNewController', ['customerRest', 'state', '$scope', 'convToHankakuKana',
    function(customerRest, state, $scope, convToHankakuKana) {
        var ctrl = this;

        ctrl.view = {
            changeBankAccountName: function() {
                ctrl.model.bankAccountName = convToHankakuKana(ctrl.model.bankAccountName);
            }
        }
    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/19.
 */

angular.module('mainApp').controller('DashboardController', ['dashboardRest', 'state', '$q',
    function(dashboardRest, state, $q) {
        var ctrl = this;

        ctrl.months = [];
        ctrl.events = [];

        state.loading = true;
        $q.all([
            dashboardRest.getProperties().$promise,             //Dashboard １列目 GET '/dashboard/properties'
            dashboardRest.getMonthlyBillingList().$promise,     //Dashboard ２列目 GET '/dashboard/monthlyBillingList'
            dashboardRest.getEachBilling().$promise,            //Dashboard ３列目 GET '/dashboard/eachBilling'
            dashboardRest.getContractStatus().$promise,         //Dashboard ４列目 GET '/dashboard/contractStatus'
            dashboardRest.getEventList().$promise               //Dashboard ５列目（イベント） GET '/dashboard/eventList'
        ]).then(function(r){
            // ctrl.buildingCount = r[0].buildingCount;
            ctrl.totalRoomCount = r[0].totalRoomCount;
            ctrl.occupiedRoomCount = r[0].occupiedRoomCount;
            ctrl.spareRoomCount = r[0].spareRoomCount;
            ctrl.occupierCount = r[0].occupierCount;

            ctrl.months = r[1];
            /**   cutoffDate
                  mishoriCount
                  billingCount
                  billingPastCount
                  demandCount
                  demandPastCount
                  resultNgCount
                  resultShortCount
                  resultOkCount      */

            ctrl.mishori2Count = r[2].mishori2Count;
            ctrl.billing2Count = r[2].billing2Count;
            ctrl.billingPast2Count = r[2].billingPast2Count;
            ctrl.demand2Count = r[2].demand2Count;
            ctrl.demandPast2Count = r[2].demandPast2Count;
            ctrl.resultShort2Count = r[2].resultShort2Count;

            ctrl.applyingCount = r[3].applyingCount;
            ctrl.beforeRenewCount = r[3].beforeRenewCount;
            ctrl.afterRenewCount = r[3].afterRenewCount;
            ctrl.taikyoYoteiCount = r[3].taikyoYoteiCount;
            ctrl.taikyoMiseisanCount = r[3].taikyoMiseisanCount;
            ctrl.refundCount = r[3].refundCount;

            ctrl.events = r[4];
            ctrl.filteredEvents = r[4];
            /**  id
                 actionDate
                 buildingCode
                 description     */
            state.loading = false;
        });

        /**
         * Dashboard １列目
         */
        ctrl.line1 = {
            buildingCountClicked: function() {
                //TODO
                alert("管理棟数");
            },
            totalRoomCountClicked: function() {
                //TODO
                alert("管理物件数");
            },
            occupiedRoomCountClicked: function() {
                //TODO
                alert("契約物件数");
            },
            spareRoomCountClicked: function() {
                //TODO
                alert("空き物件数");
            },
            occupierCountClicked: function() {
                //TODO
                alert("入居者人数");
            },
            applyingCountClicked: function() {
                //TODO
                alert("契約手続中");
            }
        };

        /**
         * Dashboard ２列目
         */
        ctrl.line2 = {
            mishoriCountClicked: function () {
                //TODO
                alert("未処理");
            },
            billingCountClicked: function () {
                //TODO
                alert("請求中");
            },
            billingPastCountClicked: function () {
                //TODO
                alert("請求中　遅延");
            },
            demandCountClicked: function () {
                //TODO
                alert("督促中");
            },
            demandPastCountClicked: function () {
                //TODO
                alert("督促中 遅延");
            },
            resultNgCountClicked: function () {
                //TODO
                alert("振替不能");
            },
            resultShortCountClicked: function () {
                //TODO
                alert("入金不足");
            },
            resultOkCountClicked: function () {
                //TODO
                alert("処理済み");
            }
        };

        /**
         * Dashboard ３列目
         */
        ctrl.line3 = {
            mishori2CountClicked: function () {
                //TODO
                alert("未処理");
            },
            billing2CountClicked: function () {
                //TODO
                alert("請求中");
            },
            billing2PastCountClicked: function () {
                //TODO
                alert("請求中　遅延");
            },
            demand2CountClicked: function () {
                //TODO
                alert("督促中");
            },
            demandPast2CountClicked: function () {
                //TODO
                alert("督促中 遅延");
            },
            resultShort2CountClicked: function () {
                //TODO
                alert("入金不足");
            }
        };

        /**
         * Dashboard ４列目
         */
        ctrl.line4 = {
            beforeRenewCountClicked: function () {
                //TODO
                alert("更新予定");
            },
            afterRenewCountClicked: function () {
                //TODO
                alert("更新処理済み");
            },
            taikyoYoteiCountClicked: function () {
                //TODO
                alert("退去予定");
            },
            taikyoMiseisanCountClicked: function () {
                //TODO
                alert("退去後未精算");
            },
            refundCountClicked: function () {
                //TODO
                alert("返金対象");
            }
        };
    }
]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MeterResultDailyController', ['meterResultDailyRest', 'listPaging', 'state', '$scope',
    function(meterResultDailyRest, listPaging, state, $scope) {
        var ctrl = this;

        var editModal = $('#event-edit-modal');

        var recDatePicker = $('#recDate');

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            return meterResultDailyRest.getMeterResultDailyList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                   id, actionDate, actionDateFormatted, title, description
                 } */
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {buildingCode: '', roomCode: '', fromDate:'', toDate:''};

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
                    id: rec.id,
                    buildingCode: rec.buildingCode,
                    roomCode: rec.roomCode,
                    recDate: rec.recDate,
                    valueEp: rec.valueEp,
                    valueWt: rec.valueWt,
                    valueHw: rec.valueHw,
                    valueCh: rec.valueCh,
                    valueOl: rec.valueOl,
                    valueGs: rec.valueGs,
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
                id:'',
                buildingCode: '',
                roomCode: '',
                recDate:'',
                valueEp: '',
                valueWt: '',
                valueHw: '',
                valueCh: '',
                valueOl: '',
                valueGs: '',
                delete: false
            },
            model: angular.copy(this.modelInit),
            show: function() {
                editModal.modal('show');
                editModal.on('hidden.bs.modal', function () {
                    ctrl.editModal.model = angular.copy(ctrl.editModal.modelInit);
                    $scope.eventInputForm.$setPristine()
                });
            },
            /**
             * 更新ボタンクリック
             */
            onClickOk: function(form) {
                if(!ctrl.editModal.model.delete && form.$invalid) return;

                if(ctrl.editModal.model.delete) {
                    //削除
                    eventCalendarRest.deleteEventCalendar({id: ctrl.editModal.model.id}, function(res){
                        listPaging.refresh();
                        editModal.modal('hide');
                    })
                } else {
                    //登録・更新
                    eventCalendarRest.saveEventCalendar(ctrl.editModal.model, function(res){
                        listPaging.refresh();
                        editModal.modal('hide');
                    })
                }
            }
        };

    }]);



'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('EventCalendarController', ['eventCalendarRest', 'listPaging', 'state', '$scope',
    function(eventCalendarRest, listPaging, state, $scope) {
        var ctrl = this;

        var editModal = $('#event-edit-modal');

        var actionDatePicker = $('#actionDate');

        //検索の為に呼び出される関数をページングオブジェクトに登録
        listPaging.triggerFunction = function() {
            listPaging.setLoading(true);
            return eventCalendarRest.getEventCalendarList(listPaging.mergeParam(ctrl.param), function(res) {
                /** 結果オブジェクトのプロパティ名 {
                   id, actionDate, actionDateFormatted, title, description
                 } */
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        //検索パラメーター
        ctrl.paramInit = {words: '', fromDate:'', toDate:''};

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
                    id: rec.id,
                    status: rec.status,
                    actionDate: rec.actionDate,
                    title: clearTag(rec.title), //ハイライトタグを取り除く
                    description: clearTag(rec.description), //ハイライトタグを取り除く
                    delete: false
                };
                ctrl.editModal.show();
                actionDatePicker.datepicker('update', rec.actionDate);
            },

            onClickAddButton: function() {
                ctrl.editModal.title = '登録';
                ctrl.editModal.show();
//                ctrl.editModal.model.actionDate='1900/01/01';
                actionDatePicker.datepicker('update', '');
            }

        };

        //モーダル
        ctrl.editModal = {
            title: '',
            okButtonName: '',
            modelInit: {
                id:'',
                status: '',
                actionDate:'',
                title:'',
                description: '',
                delete: false
            },
            model: angular.copy(this.modelInit),
            show: function() {
                editModal.modal('show');
                editModal.on('hidden.bs.modal', function () {
                    ctrl.editModal.model = angular.copy(ctrl.editModal.modelInit);
                    $scope.eventInputForm.$setPristine()
                });
            },
            /**
             * 更新ボタンクリック
             */
            onClickOk: function(form) {
                if(!ctrl.editModal.model.delete && form.$invalid) return;

                if(ctrl.editModal.model.delete) {
                    //削除
                    eventCalendarRest.deleteEventCalendar({id: ctrl.editModal.model.id}, function(res){
                        listPaging.refresh();
                        editModal.modal('hide');
                    })
                } else {
                    //登録・更新
                    eventCalendarRest.saveEventCalendar(ctrl.editModal.model, function(res){
                        listPaging.refresh();
                        editModal.modal('hide');
                    })
                }
            }
        };

    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MainController', ['$location', '$route', 'masters', 'userInfo', 'state', 'confirmModal', 'listPaging', 'patterns',
    function($location, $route, masters, userInfo, state, confirmModal, listPaging, patterns) {
        var main = this;

        //Viewの切り替えメソッド
        main.changeView = function(path) {
            state.activeView = path;
            $location.path(path);
        };

        //サイドバーメニューと連動
        main.isActiveView = function(path) {
            if(Array.isArray(path))
                return path.indexOf(state.activeView) >= 0;
            else
                return state.activeView == path;
        };

        var oldGroupType = '';
        main.managementGroupChanged = function() {
            //初期ロード、又は業務管理グループが変更されたとき
            state.refreshManagementId(function() {
                var newGroupType = main.state.selectedManagementType;
                if(oldGroupType == newGroupType) {
                    //画面をリロードしてデータを反映させる
                    $route.reload();
                } else {
                    if (newGroupType == 'O') main.changeView('/dashboard');
                    else if (newGroupType == 'S') main.changeView('/system');
                    else if (newGroupType == 'M') main.changeView('/propertiesMaster');
                    oldGroupType = newGroupType;
                }
            })
        };

        main.changeView('/dashboard');

        masters.refreshMasters();
        userInfo.refreshUserInfo();
        main.managementGroupChanged();

        main.masters = masters;
        main.userInfo = userInfo;
        main.state = state;
        main.confirm = confirmModal;
        main.paging = listPaging;
        main.patterns = patterns;
    }]);

'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MonthlyBillingController', ['monthlyBillingRest', 'listPaging', 'confirmModal', '$timeout', 'state', '$q',
    function(monthlyBillingRest, listPaging, confirmModal, $timeout, state, $q) {
        var ctrl = this;
        state.loading = true;
        //チェックボックスの初期化
        ctrl.checkbox = [];

        //月次処理ステータス／抽出処理の進捗率　取得
        var statusRefresh = function(){
            return monthlyBillingRest.getProcessingStatusList(function (res) {
                /* Listデータの内容
                 cutoffDate　・・・締日（年月日配列：Key用）
                 cutoffDateFormatted　・・・締日（表示用）
                 billingCount　・・・請求処理対象件数
                 monthlyStatus　・・・請求処理可能（W:データ確認中 A:請求処可能　P:抽出処理中 S:抽出処理済）
                 processingRate　・・・処理中の進捗率：0 - 100(%)
                 */
                if(!angular.equals(ctrl.processingStatusList, res))
                    ctrl.processingStatusList = res;
                angular.forEach(res, function(e) {
                    if(e.monthlyStatus=='P' && state.activeView=='/monthlyBilling') {
                        //処理中のレコードがある場合、一定時間後に再取得
                        $timeout(function() {
                            statusRefresh();
                        }, 1000);
                    }
                });
            }).$promise;
        };

        //請求方法毎の処理ステータス
        var billingMethodStatusRefresh = function() {
            return monthlyBillingRest.getBillingMethodList(function (res) {
                /* Listデータの内容
                 code　・・・締日（年月日配列：Key用）
                 name　・・・締日（表示用）
                 status　・・・N:準備中 A:処理可能 P:処理中 C:完了
                 billingCount　・・・請求処理対象件数
                 processingRate　・・・処理中の進捗率：0 - 100(%)
                 fileId　・・・ファイルID
                 fileName　・・・ファイル名
                 */
                if(!angular.equals(ctrl.billingMethodList, res))
                    ctrl.billingMethodList = res;
                angular.forEach(res, function(e) {
                    if(e.status=='P' && state.activeView=='/monthlyBilling') {
                        //処理中の場合、一定時間後に再取得
                        $timeout(function () {
                            billingMethodStatusRefresh();
                        }, 1000);
                    }
                });
            }).$promise;
        };

        ctrl.createStatement = function(dt, fmtDt) {
            confirmModal.title="請求データ作成処理";
            confirmModal.body=fmtDt + "の請求データを作成します。<br/>よろしいですか？";
            confirmModal.open(function() {
                alert("ok clicked!");
            });
        };

        ctrl.settle = function(dt, fmtDt) {
            confirmModal.title="口振請求データ作成処理";
            confirmModal.body= "<div style='color:red;font-weight: bold' class='alert alert-danger'>" +
                "<i class='fa fa-exclamation-circle'></i> 口振請求データを作成すると、請求データの編集は出来なくなります</div>" +
                fmtDt + "の請求データを確定し口振請求データを作成します。<br/>よろしいですか？" ;
            confirmModal.open(function() {
                alert("ok clicked!");
            });
        };

        ctrl.checkAll = function(check) {
            ctrl.checkbox = ctrl.checkbox.map(function () {return check});
        };

        //年月データ取得
        var getYearMonthList = function() {
            return monthlyBillingRest.getYearMonthList(function (res) {
                var yearMonthTable = {};

                //年月の一覧から年をKey、月の配列をValueとした2次元連想配列を作成する
                angular.forEach(res, function (p) {
                    if (Array.isArray(yearMonthTable[p.year]))
                        yearMonthTable[p.year].push(p.month);
                    else
                        yearMonthTable[p.year] = [p.month];
                });
                ctrl.yearMonthTable = yearMonthTable;

                //連想配列から年のリストを作成
                var yearList = [];
                for (var key in yearMonthTable) {
                    yearList.push(key)
                }

                //年のリスト降順に並べ替える
                ctrl.yearList = yearList.sort(
                    function (a, b) {
                        if (a < b) return 1;
                        if (a > b) return -1;
                        return 0;
                    }
                );

                //年の初期値をセット
                ctrl.selectedYear = ctrl.yearList[0];
                ctrl.selectedMonth = Math.max.apply(null, ctrl.yearMonthTable[ctrl.selectedYear]);
                ctrl.selectedStatus = "SK";
                listPaging.refresh();
            }).$promise;
        };

        ctrl.mishoriCount = 0;

        ctrl.yearChanged = function(year) {
            //年が変更されたとき、その年の月の最大値を選択させる
            ctrl.monthClicked(Math.max.apply(null, ctrl.yearMonthTable[year]));
        };

        ctrl.monthClicked = function(month) {
            //クリックされた月を選択状態にする
            ctrl.selectedMonth = month;
            listPaging.refresh();
        };

        ctrl.statusClicked = function(status) {
            ctrl.selectedStatus = status;
            listPaging.refresh();
        };

        // 検索条件画面　表示／非表示
        ctrl.showSearchPaneClicked = function (bool) {
            ctrl.showSearchPane = !bool;
            listPaging.refresh();
        };

        //条件クリア
        ctrl.searchPaneClear = function () {
            ctrl.selectedBuildingCode = '';
            ctrl.searchText = '';
            ctrl.selectedPaymentMethod = '';
            listPaging.refresh();
        };

        // 行クリック
        ctrl.rowClicked = function(index, rec) {
            alert("clicked row is " + index); //todo
        };

        //ページングオブジェクトへリクエスト関数を登録
        listPaging.triggerFunction = function() {
            var param = {
                offset: listPaging.getOffsetForRetrieve(),
                limit: listPaging.getLimitForRetrieve(),
                year: ctrl.selectedYear,
                month: ctrl.selectedMonth,
                status: ctrl.selectedStatus,
                buildingCode: ctrl.showSearchPane?ctrl.selectedBuildingCode:'',
                paymentMethod: ctrl.showSearchPane?ctrl.selectedPaymentMethod:'',
                searchText: ctrl.showSearchPane?ctrl.searchText:''
            };

            //サーバーへリクエスト
            listPaging.setLoading(true);
            monthlyBillingRest.getBillingDataList(param, function(res){
                listPaging.setData(res);
                listPaging.setLoading(false);
            });
        };

        $q.all([statusRefresh(), billingMethodStatusRefresh(), getYearMonthList()]).then(function(r){
            state.loading = false;
        })
    }]
);

'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('PropertiesMasterController', [
    'propertiesMasterRest', 'listPaging', 'state', '$scope', 'masters',
    function(propertiesMasterRest, listPaging, state, $scope, masters) {
        var ctrl = this;

        ctrl.sortableOptions = {
            axis: 'y',
            update: function() {
                masters.managementGroupList.forEach(function(e, i){
                    e.sortOrder = i;
                });
                propertiesMasterRest.saveManagementGroups(masters.managementGroupList, function(rec){
                })

            }
        };

        //管理グループ ビュー
        ctrl.groupView = {
            onClickEdit: function(rec) {
                ctrl.groupModal.title = '管理グループの更新';
                ctrl.groupModal.model = {
                    id: rec.id,
                    name: rec.name,
                    type: rec.type,
                    sortOrder: rec.sortOrder,
                    delete: false
                };
                ctrl.groupModal.show();
            },

            onClickAdd: function() {
                ctrl.groupModal.title = '管理グループの登録';
                ctrl.groupModal.model = angular.copy(ctrl.groupModal.modelInit);
                ctrl.groupModal.show();
            }
        };

        //管理グループ モーダル
        ctrl.groupModal = {
            title: '',
            okButtonName: '',
            modelInit: { //初期化データ
                id:'',
                name: '',
                type:'O',
                sortOrder:'9999',
                delete: false
            },
            model: angular.copy(this.modelInit),
            show: function() {
                $('#group-edit-modal').modal('show');
                $('#group-edit-modal').on('hidden.bs.modal', function () {
                    ctrl.groupModal.model = angular.copy(ctrl.groupModal.modelInit);
                    $scope.groupInputForm.$setPristine()
                });
            },
            /**
             * 更新ボタンクリック
             */
            onClickOk: function(form) {
                if(!ctrl.groupModal.model.delete && form.$invalid) return;

                if(ctrl.groupModal.model.delete) {
                    //削除
                    propertiesMasterRest.deleteManagementGroup({id: ctrl.groupModal.model.id}, function(res){
                        masters.refreshMasters();
                        $('#group-edit-modal').modal('hide');
                    })
                } else {
                    //登録・更新　※１件だが配列として渡す
                    propertiesMasterRest.saveManagementGroups([ctrl.groupModal.model], function(res){
                        masters.refreshMasters();
                        $('#group-edit-modal').modal('hide');
                    })
                }
            }
        };

        //建物管理ビュー
        ctrl.buildingView = {
            onClickEdit: function(rec) {
                ctrl.buildingModal.title = '建物の更新';
                ctrl.buildingModal.newRec = false;
                ctrl.buildingModal.model = {
                    code: rec.code,
                    name: rec.name,
                    address: rec.address,
                    cutoffDay: rec.cutoffDay,
                    delete: rec.delete,
                    facilities: angular.copy(rec.facilities)
                };
                ctrl.buildingModal.show();
            },

            onClickAdd: function() {
                ctrl.buildingModal.title = '建物の登録';
                ctrl.buildingModal.newRec = true;
                ctrl.buildingModal.model = angular.copy(ctrl.buildingModal.modelInit);
                ctrl.buildingModal.show();
            }
        };

        //建物モーダル
        ctrl.buildingModal = {
            newRec:false,
            title: '',
            okButtonName: '',
            modelInit: { //初期化データ
                code:'',
                name: '',
                address:'',
                cutoffDay:'20',
                delete: false,
                facilities: []
            },
            model: angular.copy(this.modelInit),
            show: function() {
                $('#building-edit-modal').modal('show');
                $('#building-edit-modal').on('hidden.bs.modal', function () {
                    ctrl.buildingModal.model = angular.copy(ctrl.buildingModal.modelInit);
                    $scope.buildingInputForm.$setPristine()
                });
            },
            /**
             * 更新ボタンクリック
             */
            onClickOk: function(form) {
                if(!ctrl.buildingModal.model.delete && form.$invalid) return;

                if(ctrl.buildingModal.model.delete) {
                    //削除
                    propertiesMasterRest.deleteBuilding({code: ctrl.buildingModal.model.code}, function(res){
                        masters.refreshMasters();
                        $('#building-edit-modal').modal('hide');
                    })
                } else {
                    //登録・更新
                    propertiesMasterRest.saveBuilding(ctrl.buildingModal.model, function(res){
                        masters.refreshMasters();
                        $('#building-edit-modal').modal('hide');
                    })
                }
            }
        };

    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('SystemController', ['systemRest', 'listPaging', 'state', '$scope',
    function(systemRest, listPaging, state, $scope) {
        var ctrl = this;

//         ctrl.param = {words: '', fromDate:'', toDate:''};
//         listPaging.triggerFunction = function() {
//             listPaging.setLoading(true);
//             return eventCalendarRest.getEventCalendarList(listPaging.mergeParam(ctrl.param), function(res) {
//                 /**
//                  id
//                  actionDate
//                  actionDateFormatted
//                  title
//                  description
//                  */
//
//                 listPaging.setData(res);
//                 listPaging.setLoading(false);
//             });
//         };
//
//         //メインビュー
//         ctrl.view = {
//
//             onClickEditButton: function(rec) {
//                 ctrl.editModal.title = '更新';
//                 ctrl.editModal.model = {
//                     id: rec.id,
//                     status: rec.status,
//                     actionDate: rec.actionDate,
//                     title: clearTag(rec.title), //ハイライトタグを取り除く
//                     description: clearTag(rec.description), //ハイライトタグを取り除く
//                     delete: false
//                 };
//                 ctrl.editModal.show();
//                 $('#actionDate').datepicker('update', rec.actionDate);
//             },
//
//             onClickAddButton: function() {
//                 ctrl.editModal.title = '登録';
//                 ctrl.editModal.show();
// //                ctrl.editModal.model.actionDate='1900/01/01';
//                 $('#actionDate').datepicker('update', '');
//             }
//
//         };
//
//         //モーダル
//         ctrl.editModal = {
//             title: '',
//             okButtonName: '',
//             modelInit: {
//                 id:'',
//                 status: '',
//                 actionDate:'',
//                 title:'',
//                 description: '',
//                 delete: false
//             },
//             model: angular.copy(this.modelInit),
//             show: function() {
//                 $('#event-edit-modal').modal('show');
//                 $('#event-edit-modal').on('hidden.bs.modal', function () {
//                     ctrl.editModal.model = angular.copy(ctrl.editModal.modelInit);
//                     $scope.eventInputForm.$setPristine()
//                 });
//             },
//             /**
//              * 更新ボタンクリック
//              */
//             onClickOk: function(form) {
//                 if(!ctrl.editModal.model.delete && form.$invalid) return;
//
//                 if(ctrl.editModal.model.delete) {
//                     //削除
//                     eventCalendarRest.deleteEventCalendar({id: ctrl.editModal.model.id}, function(res){
//                         listPaging.refresh();
//                         $('#event-edit-modal').modal('hide');
//                     })
//                 } else {
//                     //登録・更新
//                     eventCalendarRest.saveEventCalendar(ctrl.editModal.model, function(res){
//                         listPaging.refresh();
//                         $('#event-edit-modal').modal('hide');
//                     })
//                 }
//             }
//         };
//
        listPaging.refresh();
    }]);


'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('UtilitiesMasterController', ['utilitiesMasterRest', 'listPaging', 'state', '$scope',
    function(utilitiesMasterRest, listPaging, state, $scope) {
        var ctrl = this;

//         ctrl.param = {words: '', fromDate:'', toDate:''};
//         listPaging.triggerFunction = function() {
//             listPaging.setLoading(true);
//             return eventCalendarRest.getEventCalendarList(listPaging.mergeParam(ctrl.param), function(res) {
//                 /**
//                  id
//                  actionDate
//                  actionDateFormatted
//                  title
//                  description
//                  */
//
//                 listPaging.setData(res);
//                 listPaging.setLoading(false);
//             });
//         };
//
//         //メインビュー
//         ctrl.view = {
//
//             onClickEditButton: function(rec) {
//                 ctrl.editModal.title = '更新';
//                 ctrl.editModal.model = {
//                     id: rec.id,
//                     status: rec.status,
//                     actionDate: rec.actionDate,
//                     title: clearTag(rec.title), //ハイライトタグを取り除く
//                     description: clearTag(rec.description), //ハイライトタグを取り除く
//                     delete: false
//                 };
//                 ctrl.editModal.show();
//                 $('#actionDate').datepicker('update', rec.actionDate);
//             },
//
//             onClickAddButton: function() {
//                 ctrl.editModal.title = '登録';
//                 ctrl.editModal.show();
// //                ctrl.editModal.model.actionDate='1900/01/01';
//                 $('#actionDate').datepicker('update', '');
//             }
//
//         };
//
//         //モーダル
//         ctrl.editModal = {
//             title: '',
//             okButtonName: '',
//             modelInit: {
//                 id:'',
//                 status: '',
//                 actionDate:'',
//                 title:'',
//                 description: '',
//                 delete: false
//             },
//             model: angular.copy(this.modelInit),
//             show: function() {
//                 $('#event-edit-modal').modal('show');
//                 $('#event-edit-modal').on('hidden.bs.modal', function () {
//                     ctrl.editModal.model = angular.copy(ctrl.editModal.modelInit);
//                     $scope.eventInputForm.$setPristine()
//                 });
//             },
//             /**
//              * 更新ボタンクリック
//              */
//             onClickOk: function(form) {
//                 if(!ctrl.editModal.model.delete && form.$invalid) return;
//
//                 if(ctrl.editModal.model.delete) {
//                     //削除
//                     eventCalendarRest.deleteEventCalendar({id: ctrl.editModal.model.id}, function(res){
//                         listPaging.refresh();
//                         $('#event-edit-modal').modal('hide');
//                     })
//                 } else {
//                     //登録・更新
//                     eventCalendarRest.saveEventCalendar(ctrl.editModal.model, function(res){
//                         listPaging.refresh();
//                         $('#event-edit-modal').modal('hide');
//                     })
//                 }
//             }
//         };
//
        listPaging.refresh();
    }]);

