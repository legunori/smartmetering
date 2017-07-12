'use strict';
/**
 * Created by Yoichi Masumoto@Valsys,inc. on 2016/08/19.
 */

angular.module('mainApp').controller('MainController', ['$location', '$route', 'masters', 'state', 'confirmModal', 'listPaging', 'patterns',
    function($location, $route, masters, state, confirmModal, listPaging, patterns) {
        var main = this;

        //Viewの切り替えメソッド
        main.changeView = function(path) {
            //state.activeView = path;
            //$location.path(path);
            state.changeView(path);
        };

        //サイドバーメニューと連動
        main.isActiveView = function(path) {
            if(Array.isArray(path))
                return path.indexOf(state.activeView) >= 0;
            else
                return state.activeView == path;
        };

        main.changeView('/dashboard');

        masters.refreshMasters();

        main.masters = masters;
        main.state = state;
        main.confirm = confirmModal;
        main.paging = listPaging;
        main.patterns = patterns;
    }]);
