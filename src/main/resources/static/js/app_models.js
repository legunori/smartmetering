'use strict';
/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/20.
 */

//モデル
angular.module('modelApp')

    //共通マスタデータのリスト＆マップ
    .factory('masters', ['commonRest', function(commonRest) {
        return {
            //建物List = [{code, name, managementId}]
            buildingList: [],
            //建物Map = [code -> name]
            buildingMap: {},
            //部屋List = [{buildingCode, roomCode, settingPattern}]
            roomList: [],
            //検針ステータスList = [{name}]
            meteringStatusMasterList: [],
            //検針ステータスList = [{month}]
            meterMonthList: [],

            //マスタ情報の読込み
            refreshMasters: function() {
                var masters = this;
                //建物一覧の取得
                commonRest.getBuildingList(function(res) {
                    //List
                    masters.buildingList = res;
                    //Map
                    angular.forEach(res, function(p) {
                        masters.buildingMap[p.code] = p.name;
                    });
                });
                //部屋一覧の取得
                commonRest.getRoomList(function(res) {
                    //List
                    masters.roomList = res;
                });
                //検針ステータスマスターの取得
                commonRest.getMeteringStatusMasterList(function(res) {
                    //List
                    masters.meteringStatusMasterList = res;
                });
                //検針年月リスト
                commonRest.getMeterMonthList(function(res){
                	//List
                	masters.meterMonthList = res;
                });
            }
        };
    }])


    //アプリケーションの状態を保持する変数
    .factory('state',['$location', function($location) {
        return {
            activeView:'',
            loading:false,
            param:{},

            changeView:function(path, param) {
                this.param = param;
                this.activeView = path;
                $location.path(path);
            }
        };
    }])

    //バリデーションのパターン
    .factory('patterns',  [function() {
        return {
            emailRegex      : /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/,
            postCodeRegex   : /^(\d{3})-?(\d{4})$/,
            bankAcNoRegex   : /^\d{7}$/,
            bankAcNameRegex : /^[ｱ-ﾝﾞﾟ0-9A-Z\\(\\)\\.\- a-zあ-んア-ン０-９（）－ー．　]+$/
        };
    }])

    //確認用モーダル画面の設定
    .factory('confirmModal', [function() {
    return {
        title:"",
        body:"",
        okBtn:"はい",
        cancelBtn:"いいえ",
        okClicked: function(){},
        open: function(callbackFunc) {
            var p = this;
            this.okClicked = callbackFunc;
            var modal = $('#confirm-modal');
            modal.modal('show');
            modal.on('hidden.bs.modal', function (e) {
                p.title = "";
                p.body = "";
            });
        }
    };
}]);