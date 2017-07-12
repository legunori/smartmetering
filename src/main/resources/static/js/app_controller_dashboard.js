'use strict';
/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/19.
 */

angular.module('mainApp').controller('DashboardController', ['dashboardRest', 'state', '$q',
    function(dashboardRest, state, $q) {
        var ctrl = this;

        ctrl.months = [];
        ctrl.meteringStatus = [];

        //Viewの切り替えメソッド
        //        ctrl.changeView = function(path) {
        //    state.activeView = path;
        //    $location.path(path);
        //};

        state.loading = true;
        $q.all([
            dashboardRest.getMeteringStatus().$promise          //Dashboard １列目 GET '/dashboard/meteringStatus'
        ]).then(function(r){
            ctrl.meteringStatus = r[0];

            state.loading = false;
        });

        /**
         * Dashboard １列目
         */
        ctrl.line1 = {
            runningCountClicked: function(buildingCode) {
                state.changeView('/meteringStatus',{buildingCode:buildingCode, status:'running'});
            },
            warningCountClicked: function(buildingCode) {
                state.changeView('/meteringStatus',{buildingCode:buildingCode, status:'warning'});
            },
            stoppingCountClicked: function(buildingCode) {
                state.changeView('/meteringStatus',{buildingCode:buildingCode, status:'stopping'});
            }
        };

    }
]);
