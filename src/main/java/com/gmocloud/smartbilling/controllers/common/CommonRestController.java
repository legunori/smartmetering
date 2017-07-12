package com.gmocloud.smartbilling.controllers.common;

import static com.gmocloud.smartbilling.dao.specifictions.RoomMeterConfigSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmocloud.smartbilling.dao.entities.LoginUserEntity;
import com.gmocloud.smartbilling.dao.entities.RoomMeterConfigEntity;
import com.gmocloud.smartbilling.dao.repositories.RoomMeterConfigRepository;
import com.gmocloud.smartbilling.security.UserSession;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/19.
 */
@RestController
public class CommonRestController {

    @Autowired
    private RoomMeterConfigRepository roomMeterConfigRepository;

    //ユーザー情報
    @RequestMapping(value = "/common/userInfo")
    public UserInfoJsonModel getUserInfo(@AuthenticationPrincipal UserSession user) {
        LoginUserEntity loginUserEntity = user.getUserEntity();
        return new UserInfoJsonModel(
                loginUserEntity.getUserName(), loginUserEntity.getMailAddress());
    }

    //建物一覧
    @RequestMapping(value = "/common/buildingList")
    public List<BuildingJsonModel> getBildingList() {

        List<BuildingJsonModel> jsonModel = new ArrayList<>();
        // jsonModel.add(new BuildingJsonModel("SAP00001","セルリアンタワー"));
        // jsonModel.add(new BuildingJsonModel("SAP00002","グランフロントタワー"));
        // jsonModel.add(new BuildingJsonModel("SAP00003","エストラストビル"));
        // jsonModel.add(new BuildingJsonModel("SAP00004","シキシマビル"));
        // jsonModel.add(new BuildingJsonModel("SAP00005","レクスンビル"));

        // TODO テーブルより取得
        jsonModel.add(new BuildingJsonModel("SAP00001","サンシャイン２１"));
        jsonModel.add(new BuildingJsonModel("SAP00002","サンシャイン弐番館"));
        jsonModel.add(new BuildingJsonModel("SAP00003","アン・セリジェ壱番館"));
        jsonModel.add(new BuildingJsonModel("SAP00004","アン・セリジェ弐番館"));
        jsonModel.add(new BuildingJsonModel("SAP00005","札幌ビオス館"));

        return jsonModel;

    }

    //部屋一覧
    @RequestMapping(value = "/common/roomList")
    public List<RoomJsonModel> getRoomList(){
        List<RoomMeterConfigEntity> roomMeterConfigEntities = roomMeterConfigRepository.findAll(
                where(activeRoomList())
                );
        return roomMeterConfigEntities.stream().map(r ->
                new RoomJsonModel(r.getBuildingCode(), r.getRoomCode())
        ).collect(Collectors.toList());
    }

    //検針ステータス
    @RequestMapping(value = "/common/meteringStatusMasterList")
    public List<CodeJsonModel> getMmeteringStatusMasterList() {
        List<CodeJsonModel> jsonModel = new ArrayList<>();
        jsonModel.add(new CodeJsonModel("running"));
        jsonModel.add(new CodeJsonModel("warning"));
        jsonModel.add(new CodeJsonModel("stopping"));
        return jsonModel;
    }
    @Data
    @AllArgsConstructor
    private static class CodeAndNameJsonModel {
        private String code;
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class CodeJsonModel {
        private String code;
    }

    @Data
    @AllArgsConstructor
    private static class BuildingJsonModel {
        private String code;
        private String name;
    }
    @Data
    @AllArgsConstructor
    private static class RoomJsonModel {
        private String buildingCode;
        private String roomCode;
    }
    @Data
    @AllArgsConstructor
    private static class UserInfoJsonModel {
        private String userName;
        private String mailAddress;
    }
}
