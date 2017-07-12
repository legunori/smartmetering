'use strict';
/**
 * Created by masu on 2015/09/18.
 */
angular.module('mainApp')
    //ぺジネイション
    .factory('listPaging', ['$timeout', function ($timeout) {
        return {
            pageLimit: 10,
            bulkIndex: 1,
            hasBulkPrevious:false,
            hasBulkNext:false,
            list: [],
            listSize: 0,
            currentPage: 1,
            maxPageCntInBulk: 1,
            rowsInPage: 15, //10, 15, 20, 25, 30, 40, 50　※ユーザの設定により変わる
            listInPage: [],
            previousDisabled: false,
            nextDisabled: false,
            pagination: [], // {number, active}
            showPageSelection:[
                {count:10, limit:10, name:'10件／ページ'},
                {count:15, limit:10, name:'15件／ページ'},
                {count:20, limit:10, name:'20件／ページ'},
                {count:25, limit:10, name:'25件／ページ'},
                {count:30, limit:10, name:'30件／ページ'},
                {count:40, limit:5, name:'40件／ページ'},
                {count:50, limit:5, name:'50件／ページ'}
            ],
            reset: function() {
                this.list = [];
                this.listInPage = [];
                this.pagination = [];
                this.listSize = 0;
            },
            setData: function(data) {
                this.list = data;
                this.listSize = data.length;
                this.maxPageCntInBulk = Math.ceil(this.listSize / this.rowsInPage);
                if(this.maxPageCntInBulk > this.pageLimit) this.maxPageCntInBulk = this.pageLimit;
                this.hasBulkPrevious=this.bulkIndex>1;
                this.hasBulkNext=(this.listSize > (this.rowsInPage*this.pageLimit));

                this.switchTo(this.currentPage);
            },
            switchTo:function(page){
                if(page < this.currentPage && this.previousDisabled) return;
                if(page > this.currentPage && this.nextDisabled) return;


                if(page<=0&&this.bulkIndex<=1) {//ボタンの2重クリック対応
                    page=1;
                    this.bulkIndex=1;
                }
                if(page <= this.pageLimit * (this.bulkIndex-1)) {
                    this.currentPage = page;
                    this.bulkIndex--;
                    this.triggerFunction();
                    return;
                } else if(page > this.pageLimit * this.bulkIndex) {
                    this.currentPage = page;
                    this.bulkIndex++;
                    this.triggerFunction();
                    return;
                }

                var pageNoInBulk = (this.pageLimit*(this.bulkIndex-1));
                this.pagination = [];
                for(var i=0; i<this.maxPageCntInBulk;i++) {
                    this.pagination[i] = {number:pageNoInBulk+i+1, active:pageNoInBulk+i+1==page}
                }

                this.listInPage = this.list.slice((page-pageNoInBulk-1)*this.rowsInPage, (page-pageNoInBulk)*this.rowsInPage);
                this.nextDisabled = page==this.maxPageCntInBulk + (this.pageLimit*(this.bulkIndex-1)) && !this.hasBulkNext;
                this.previousDisabled = page==1 && !this.hasBulkPrevious;
                this.currentPage = page;

                if(this.currentPage > 1 && this.listInPage.length == 0) {
                    this.switchTo(1);
                }
            },
            refresh:function(milliSec) {
                if(milliSec) {
                    if(this.timer) clearTimeout(this.timer);
                    var p = this;
                    this.timer = setTimeout(function(){
                        p.triggerFunction();
                        p.timer = null;
                    },milliSec);
                } else {
                    return this.triggerFunction();
                }
            },
            triggerFunction: function(bulk) {}, //Paginationを実装するコントロール側でセット
            isLoading:false,
            setLoading: function(loading){
                if(loading)
                    this.isLoading = loading;
                else {
                    var p = this;
                    $timeout(function(){
                        p.isLoading = false;
                    }, 540);
                }

            },
            getOffsetForRetrieve: function() {
                var size = this.pageLimit * this.rowsInPage;
                return (this.bulkIndex - 1) * size;
            },
            getLimitForRetrieve: function(){
                return this.pageLimit * this.rowsInPage + 1;
            },
            mergeParam: function(param) {
                var p = this;
                return angular.merge(param, {
                    page: p.bulkIndex - 1,
                    size: this.pageLimit * this.rowsInPage + 1
                });
            }
        }
    }]);

angular.module('mainApp').controller('partPaginationController',
    ['$scope', 'listPaging', '$localStorage', function($scope, listPaging, $localStorage) {
    $scope.listPaging = listPaging;

    $scope.changeRowsInPage = function() {
        listPaging.rowsInPage = $scope.$storage.showInPage.count;
        listPaging.pageLimit = $scope.$storage.showInPage.limit;
        listPaging.refresh();
    };

    //local storage default
    $scope.$storage = $localStorage.$default({
        showInPage:{count:listPaging.rowsInPage, limit:listPaging.pageLimit}
    });

    if($scope.$storage.showInPage.count)
        listPaging.rowsInPage = $scope.$storage.showInPage.count;
    if($scope.$storage.showInPage.limit)
        listPaging.pageLimit = $scope.$storage.showInPage.limit;
}]);
