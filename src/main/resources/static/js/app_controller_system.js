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

