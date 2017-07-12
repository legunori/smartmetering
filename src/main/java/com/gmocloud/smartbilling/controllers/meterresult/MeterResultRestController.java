package com.gmocloud.smartbilling.controllers.meterresult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gmocloud.smartbilling.dao.customRepositories.MeterValueDailyCustomRepository;
import com.gmocloud.smartbilling.dao.customRepositories.MeterValueMonthlyCustomRepository;
import com.gmocloud.smartbilling.dao.entities.MeterValueDailyEntity;
import com.gmocloud.smartbilling.dao.entities.MeterValueMonthlyEntity;
import com.gmocloud.smartbilling.dao.repositories.MeterValueDailyRepository;
import com.gmocloud.smartbilling.dao.repositories.MeterValueMonthlyRepository;

import lombok.Data;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@RestController
public class MeterResultRestController {

//    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Autowired
    private MeterValueDailyCustomRepository meterValueDailyCustomRepository;

    @Autowired
    private MeterValueMonthlyCustomRepository meterValueMonthlyCustomRepository;

    @Autowired
    private MeterValueDailyRepository meterValueDailyRepository;

    @Autowired
    private MeterValueMonthlyRepository meterValueMonthlyRepository;

    //日次集計結果検索
    @RequestMapping("/meterResultDaily/meterResultDailyList")
    public List<MeterValueDailyCustomRepository.MeterValueDailyViewModel> getMeterResultDailyList(
            @RequestParam(required = false) String buildingCode,
            @RequestParam(required = false) String roomCode,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) LocalTime toTime,
            Pageable pageable )
            {

        return meterValueDailyCustomRepository.getMeterValueDailyList(buildingCode, roomCode, fromDate, toDate, toTime);
    }

    //日次集計デフォルト条件
    @RequestMapping("/meterResultDaily/meterResultDailyDefaultDate")
    public List<MeterValueDailyCustomRepository.MeterValueDailyDefaultDateViewModel> getMeterResltDailyDefaultDate()
    {
        return meterValueDailyCustomRepository.getMeterValueDailyDefaultDate();
    }

    //月次集計結果検索
    @RequestMapping("/meterResultMonthly/meterResultMonthlyList")
    public List<MeterValueMonthlyCustomRepository.MeterValueMonthlyViewModel> getMeterResultMonthlyList(
            @RequestParam(required = false) String buildingCode,
            @RequestParam(required = false) String roomCode,
            @RequestParam(required = false) String month,
            @SortDefault(sort = "month", direction = Sort.Direction.DESC) Pageable pageable )
            {
        return meterValueMonthlyCustomRepository.getMeterValueMonthlyList(buildingCode, roomCode, month);
    }

    //検針年月リスト
    @RequestMapping("/meterResultMonthly/meterMonthList")
    public List<MeterValueMonthlyCustomRepository.MeterValueMonthListViewModel> getMeterMonthList()
            {
        return meterValueMonthlyCustomRepository.getMeterValueMonthList();
    }

    //登録・更新
    @RequestMapping(value = "/meterResultDaily/save", method = RequestMethod.POST)
    public Integer saveMeterResultDaily(
            @RequestBody MeterValueDailyJsonModel model
        ) {

        //電気
        String utilitiesEp = "EP";
        MeterValueDailyEntity entityEP = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesEp));

        if ((entityEP != null) && (model.getValueEp() == null)){
            entityEP.setValue(model.getValueEp());
            meterValueDailyRepository.delete(entityEP);
        }else if ((entityEP != null) && (entityEP.getValue().compareTo(model.getValueEp()) != 0 )){
            entityEP.setValue(model.getValueEp());
            meterValueDailyRepository.saveAndFlush(entityEP);
        }else if ((entityEP == null) && (model.getValueEp() != null) && (model.getValueEp().compareTo(BigDecimal.ZERO) != 0)){
            entityEP = new MeterValueDailyEntity();
            entityEP.setRecDate(model.getRecDate());
            entityEP.setBuildingCode(model.getBuildingCode());
            entityEP.setRoomCode(model.getRoomCode());
            entityEP.setUtilitiesCode(utilitiesEp);
            entityEP.setValue(model.getValueEp());
            meterValueDailyRepository.saveAndFlush(entityEP);
        }

        //水道
        String utilitiesWt = "WT";
        MeterValueDailyEntity entityWT = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesWt));
        if ((entityWT != null) && (model.getValueWt() == null)){
            entityWT.setValue(model.getValueWt());
            meterValueDailyRepository.delete(entityWT);
        }else if ((entityWT != null) && (entityWT.getValue().compareTo(model.getValueWt()) != 0 )){
            entityWT.setValue(model.getValueWt());
            meterValueDailyRepository.saveAndFlush(entityWT);
        }else if ((entityWT == null)  && (model.getValueWt() != null) && (model.getValueWt().compareTo(BigDecimal.ZERO) != 0)){
            entityWT = new MeterValueDailyEntity();
            entityWT.setRecDate(model.getRecDate());
            entityWT.setBuildingCode(model.getBuildingCode());
            entityWT.setRoomCode(model.getRoomCode());
            entityWT.setUtilitiesCode(utilitiesWt);
            entityWT.setValue(model.getValueWt());
            meterValueDailyRepository.saveAndFlush(entityWT);
        }

        //給湯
        String utilitiesHw = "HW";
        MeterValueDailyEntity entityHw = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesHw));
        if ((entityHw != null) && (model.getValueHw() == null)){
            entityHw.setValue(model.getValueHw());
            meterValueDailyRepository.delete(entityHw);
        }else if ((entityHw != null) && (entityHw.getValue().compareTo(model.getValueHw()) != 0 )){
            entityHw.setValue(model.getValueHw());
            meterValueDailyRepository.saveAndFlush(entityHw);
        }else if ((entityHw == null) && (model.getValueHw() != null) && (model.getValueHw().compareTo(BigDecimal.ZERO) != 0)){
            entityHw = new MeterValueDailyEntity();
            entityHw.setRecDate(model.getRecDate());
            entityHw.setBuildingCode(model.getBuildingCode());
            entityHw.setRoomCode(model.getRoomCode());
            entityHw.setUtilitiesCode(utilitiesHw);
            entityHw.setValue(model.getValueHw());
            meterValueDailyRepository.saveAndFlush(entityHw);
        }

        //冷暖房
        String utilitiesCh = "CH";
        MeterValueDailyEntity entityCh = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesCh));
        if ((entityCh != null) && (model.getValueCh() == null)){
            entityCh.setValue(model.getValueCh());
            meterValueDailyRepository.delete(entityCh);
        } else if ((entityCh != null) && (entityCh.getValue().compareTo(model.getValueCh()) != 0 )){
            entityCh.setValue(model.getValueCh());
            meterValueDailyRepository.saveAndFlush(entityCh);
        }else if ((entityCh == null) && (model.getValueCh() != null) && (model.getValueCh().compareTo(BigDecimal.ZERO) != 0)){
            entityCh = new MeterValueDailyEntity();
            entityCh.setRecDate(model.getRecDate());
            entityCh.setBuildingCode(model.getBuildingCode());
            entityCh.setRoomCode(model.getRoomCode());
            entityCh.setUtilitiesCode(utilitiesCh);
            entityCh.setValue(model.getValueCh());
            meterValueDailyRepository.saveAndFlush(entityCh);
        }

        //灯油
        String utilitiesOl = "OL";
        MeterValueDailyEntity entityOl = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesOl));
        if ((entityOl != null) && (model.getValueOl() == null)) {
            entityOl.setValue(model.getValueOl());
            meterValueDailyRepository.delete(entityOl);
        }else if ((entityOl != null) && (entityOl.getValue().compareTo(model.getValueOl()) != 0 )){
            entityOl.setValue(model.getValueOl());
            meterValueDailyRepository.saveAndFlush(entityOl);
        }else if ((entityOl == null) && (model.getValueOl() != null) && (model.getValueCh().compareTo(BigDecimal.ZERO) != 0)){
            entityOl = new MeterValueDailyEntity();
            entityOl.setRecDate(model.getRecDate());
            entityOl.setBuildingCode(model.getBuildingCode());
            entityOl.setRoomCode(model.getRoomCode());
            entityOl.setUtilitiesCode(utilitiesOl);
            entityOl.setValue(model.getValueOl());
            meterValueDailyRepository.saveAndFlush(entityOl);
        }

        //ガス
        String utilitiesGs = "GS";
        MeterValueDailyEntity entityGs = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesGs));
        if ((entityGs != null) && (model.getValueGs() == null)){
            entityGs.setValue(model.getValueGs());
            meterValueDailyRepository.delete(entityGs);
        }else if ((entityGs != null) && (entityGs.getValue().compareTo(model.getValueGs()) != 0 )){
            entityGs.setValue(model.getValueGs());
            meterValueDailyRepository.saveAndFlush(entityGs);
        }else if ((entityGs == null) && (model.getValueGs() != null) && (model.getValueGs().compareTo(BigDecimal.ZERO) != 0)){
            entityGs = new MeterValueDailyEntity();
            entityGs.setRecDate(model.getRecDate());
            entityGs.setBuildingCode(model.getBuildingCode());
            entityGs.setRoomCode(model.getRoomCode());
            entityGs.setUtilitiesCode(utilitiesGs);
            entityGs.setValue(model.getValueGs());
            meterValueDailyRepository.saveAndFlush(entityGs);
        }

        //動力
        String utilitiesPw = "PW";
        MeterValueDailyEntity entityPw = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesPw));
        if ((entityPw != null) && (model.getValuePw() == null)){
            entityPw.setValue(model.getValuePw());
            meterValueDailyRepository.delete(entityPw);
        }else if ((entityPw != null) && (entityPw.getValue().compareTo(model.getValuePw()) != 0 )){
            entityPw.setValue(model.getValuePw());
            meterValueDailyRepository.saveAndFlush(entityPw);
        }else if ((entityPw == null) && (model.getValuePw() != null) && (model.getValuePw().compareTo(BigDecimal.ZERO) != 0)){
            entityPw = new MeterValueDailyEntity();
            entityPw.setRecDate(model.getRecDate());
            entityPw.setBuildingCode(model.getBuildingCode());
            entityPw.setRoomCode(model.getRoomCode());
            entityPw.setUtilitiesCode(utilitiesPw);
            entityPw.setValue(model.getValuePw());
            meterValueDailyRepository.saveAndFlush(entityPw);
        }

        //電話
        String utilitiesPh = "PH";
        MeterValueDailyEntity entityPh = meterValueDailyRepository.findOne(
                new MeterValueDailyEntity.PKey(model.getRecDate(),model.getBuildingCode(),model.getRoomCode(),utilitiesPh));
        if ((entityPh != null) && (model.getValuePh() == null)){
            entityPh.setValue(model.getValuePh());
            meterValueDailyRepository.delete(entityPh);
        }else if ((entityPh != null) && (entityPh.getValue().compareTo(model.getValuePh()) != 0 )){
            entityPh.setValue(model.getValuePh());
            meterValueDailyRepository.saveAndFlush(entityPh);
        }else if ((entityPh == null) && (model.getValuePh() != null) && (model.getValuePh().compareTo(BigDecimal.ZERO) != 0)){
            entityPh = new MeterValueDailyEntity();
            entityPh.setRecDate(model.getRecDate());
            entityPh.setBuildingCode(model.getBuildingCode());
            entityPh.setRoomCode(model.getRoomCode());
            entityPh.setUtilitiesCode(utilitiesPh);
            entityPh.setValue(model.getValuePh());
            meterValueDailyRepository.saveAndFlush(entityPh);
        }

        return 0;
    }

    //Jsonモデル
    @Data
    private static class MeterValueDailyJsonModel {
        private String buildingCode;
        private String buildingName;
        private String roomCode;
        @JsonFormat(pattern = "yyyy年MM月dd日")
        private LocalDate recDate;
        private BigDecimal valueEp;
        private BigDecimal valueWt;
        private BigDecimal valueHw;
        private BigDecimal valueCh;
        private BigDecimal valueOl;
        private BigDecimal valueGs;
        private BigDecimal valuePw;
        private BigDecimal valuePh;
    }


    //登録・更新
    @RequestMapping(value = "/meterResultMonthly/save", method = RequestMethod.POST)
    public Integer saveMeterResultMonthly(
            @RequestBody MeterValueMonthlyJsonModel model
        ) {

        //電気
        String utilitiesEp = "EP";
        MeterValueMonthlyEntity entityEP = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesEp));
        if ((entityEP != null) && (model.getValueEp() == null)){
            entityEP.setValue(model.getValueEp());
            meterValueMonthlyRepository.delete(entityEP);
        }else if ((entityEP != null) && (entityEP.getValue().compareTo(model.getValueEp()) != 0 )){
            entityEP.setValue(model.getValueEp());
            meterValueMonthlyRepository.saveAndFlush(entityEP);
        }else if ((entityEP == null) && (model.getValueEp() != null) && (model.getValueEp().compareTo(BigDecimal.ZERO) != 0)){
            entityEP = new MeterValueMonthlyEntity();
            entityEP.setMonth(model.getMonth());
            entityEP.setBuildingCode(model.getBuildingCode());
            entityEP.setRoomCode(model.getRoomCode());
            entityEP.setUtilitiesCode(utilitiesEp);
            entityEP.setValue(model.getValueEp());
            meterValueMonthlyRepository.saveAndFlush(entityEP);
        }

        //水道
        String utilitiesWt = "WT";
        MeterValueMonthlyEntity entityWT = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesWt));
        if ((entityWT != null) && (model.getValueWt() == null)){
            entityWT.setValue(model.getValueWt());
            meterValueMonthlyRepository.delete(entityWT);
        }else if ((entityWT != null) && (entityWT.getValue().compareTo(model.getValueWt()) != 0 )){
            entityWT.setValue(model.getValueWt());
            meterValueMonthlyRepository.saveAndFlush(entityWT);
        }else if ((entityWT == null) && (model.getValueWt() != null) && (model.getValueWt().compareTo(BigDecimal.ZERO) != 0)){
            entityWT = new MeterValueMonthlyEntity();
            entityWT.setMonth(model.getMonth());
            entityWT.setBuildingCode(model.getBuildingCode());
            entityWT.setRoomCode(model.getRoomCode());
            entityWT.setUtilitiesCode(utilitiesWt);
            entityWT.setValue(model.getValueWt());
            meterValueMonthlyRepository.saveAndFlush(entityWT);
        }

        //給湯
        String utilitiesHw = "HW";
        MeterValueMonthlyEntity entityHw = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesHw));
        if ((entityHw != null) && (model.getValueHw() == null)){
            entityHw.setValue(model.getValueHw());
            meterValueMonthlyRepository.delete(entityHw);
        }else if ((entityHw != null) && (entityHw.getValue().compareTo(model.getValueHw()) != 0 )){
            entityHw.setValue(model.getValueHw());
            meterValueMonthlyRepository.saveAndFlush(entityHw);
        }else if ((entityHw == null) && (model.getValueHw() != null) && (model.getValueHw().compareTo(BigDecimal.ZERO) != 0)){
            entityHw = new MeterValueMonthlyEntity();
            entityHw.setMonth(model.getMonth());
            entityHw.setBuildingCode(model.getBuildingCode());
            entityHw.setRoomCode(model.getRoomCode());
            entityHw.setUtilitiesCode(utilitiesHw);
            entityHw.setValue(model.getValueHw());
            meterValueMonthlyRepository.saveAndFlush(entityHw);
        }

        //冷暖房
        String utilitiesCh = "CH";
        MeterValueMonthlyEntity entityCh = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesCh));
        if ((entityCh != null) && (model.getValueCh() == null)){
            entityCh.setValue(model.getValueCh());
            meterValueMonthlyRepository.delete(entityCh);
        }else if ((entityCh != null) && (entityCh.getValue().compareTo(model.getValueCh()) != 0 )){
            entityCh.setValue(model.getValueCh());
            meterValueMonthlyRepository.saveAndFlush(entityCh);
        }else if ((entityCh == null) && (model.getValueCh() == null) && (model.getValueCh().compareTo(BigDecimal.ZERO) != 0)){
            entityCh = new MeterValueMonthlyEntity();
            entityCh.setMonth(model.getMonth());
            entityCh.setBuildingCode(model.getBuildingCode());
            entityCh.setRoomCode(model.getRoomCode());
            entityCh.setUtilitiesCode(utilitiesCh);
            entityCh.setValue(model.getValueCh());
            meterValueMonthlyRepository.saveAndFlush(entityCh);
        }

        //灯油
        String utilitiesOl = "OL";
        MeterValueMonthlyEntity entityOl = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesOl));
        if ((entityOl != null) && (model.getValueOl() == null)){
            entityOl.setValue(model.getValueOl());
            meterValueMonthlyRepository.delete(entityOl);
        }else if ((entityOl != null) && (entityOl.getValue().compareTo(model.getValueOl()) != 0 )){
            entityOl.setValue(model.getValueOl());
            meterValueMonthlyRepository.saveAndFlush(entityOl);
        }else if ((entityOl == null) && (model.getValueOl() != null) && (model.getValueCh().compareTo(BigDecimal.ZERO) != 0)){
            entityOl = new MeterValueMonthlyEntity();
            entityOl.setMonth(model.getMonth());
            entityOl.setBuildingCode(model.getBuildingCode());
            entityOl.setRoomCode(model.getRoomCode());
            entityOl.setUtilitiesCode(utilitiesOl);
            entityOl.setValue(model.getValueOl());
            meterValueMonthlyRepository.saveAndFlush(entityOl);
        }

        //ガス
        String utilitiesGs = "GS";
        MeterValueMonthlyEntity entityGs = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesGs));
        if ((entityGs != null) && (model.getValueGs() == null)){
            entityGs.setValue(model.getValueGs());
            meterValueMonthlyRepository.delete(entityGs);
        }else if ((entityGs != null) && (entityGs.getValue().compareTo(model.getValueGs()) != 0 )){
            entityGs.setValue(model.getValueGs());
            meterValueMonthlyRepository.saveAndFlush(entityGs);
        }else if ((entityGs == null) && (model.getValueGs() != null) && (model.getValueGs().compareTo(BigDecimal.ZERO) != 0)){
            entityGs = new MeterValueMonthlyEntity();
            entityGs.setMonth(model.getMonth());
            entityGs.setBuildingCode(model.getBuildingCode());
            entityGs.setRoomCode(model.getRoomCode());
            entityGs.setUtilitiesCode(utilitiesGs);
            entityGs.setValue(model.getValueGs());
            meterValueMonthlyRepository.saveAndFlush(entityGs);
        }

        //動力
        String utilitiesPw = "PW";
        MeterValueMonthlyEntity entityPw = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesPw));
        if ((entityPw != null) && (model.getValuePw() == null)){
            entityPw.setValue(model.getValuePw());
            meterValueMonthlyRepository.delete(entityPw);
        }else if ((entityPw != null) && (entityPw.getValue().compareTo(model.getValuePw()) != 0 )){
            entityPw.setValue(model.getValuePw());
            meterValueMonthlyRepository.saveAndFlush(entityPw);
        }else if ((entityPw == null) && (model.getValuePw() != null) && (model.getValuePw().compareTo(BigDecimal.ZERO) != 0)){
            entityPw = new MeterValueMonthlyEntity();
            entityPw.setMonth(model.getMonth());
            entityPw.setBuildingCode(model.getBuildingCode());
            entityPw.setRoomCode(model.getRoomCode());
            entityPw.setUtilitiesCode(utilitiesPw);
            entityPw.setValue(model.getValuePw());
            meterValueMonthlyRepository.saveAndFlush(entityPw);
        }

        //電話
        String utilitiesPh = "PH";
        MeterValueMonthlyEntity entityPh = meterValueMonthlyRepository.findOne(
                new MeterValueMonthlyEntity.PKey(model.getMonth(),model.getBuildingCode(),model.getRoomCode(),utilitiesPh));
        if ((entityPh != null) && (model.getValuePh() == null)){
            entityPh.setValue(model.getValuePh());
            meterValueMonthlyRepository.delete(entityPh);
        }else if ((entityPh != null) && (entityPh.getValue().compareTo(model.getValuePh()) != 0 )){
            entityPh.setValue(model.getValuePh());
            meterValueMonthlyRepository.saveAndFlush(entityPh);
        }else if ((entityPh == null) && (model.getValuePh() != null) && (model.getValuePh().compareTo(BigDecimal.ZERO) != 0)){
            entityPh = new MeterValueMonthlyEntity();
            entityPh.setMonth(model.getMonth());
            entityPh.setBuildingCode(model.getBuildingCode());
            entityPh.setRoomCode(model.getRoomCode());
            entityPh.setUtilitiesCode(utilitiesPh);
            entityPh.setValue(model.getValuePh());
            meterValueMonthlyRepository.saveAndFlush(entityPh);
        }

        return 0;
    }

    //Jsonモデル
    @Data
    private static class MeterValueMonthlyJsonModel {
        private String buildingCode;
        private String buildingName;
        private String roomCode;
        private String month;
        private BigDecimal valueEp;
        private BigDecimal valueWt;
        private BigDecimal valueHw;
        private BigDecimal valueCh;
        private BigDecimal valueOl;
        private BigDecimal valueGs;
        private BigDecimal valuePw;
        private BigDecimal valuePh;
    }

}
