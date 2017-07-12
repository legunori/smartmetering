package com.gmocloud.smartbilling.controllers.meteringlivedata;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmocloud.smartbilling.dao.customRepositories.SmartenergyRoomPulseCustomRepository;
import com.gmocloud.smartbilling.dao.customRepositories.TanddRecordPulseCustomRepository;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@RestController
public class MeteringLiveDataRestController {

    @Autowired
    private SmartenergyRoomPulseCustomRepository smartenergyRoomPulseCustomRepository;

    @Autowired
    private TanddRecordPulseCustomRepository tanddRecordPulseCustomRepository;

    //SmartenergyRoomPulse
    @RequestMapping("/meteringLiveData/meteringLiveDataSmartenergyList")
    public List<SmartenergyRoomPulseCustomRepository.SmartenergyRoomPulseViewModel> getMeteringDataSmartenergyList(
            @RequestParam(required = false) String buildingCode,
            @RequestParam(required = false) String roomCode,
            @RequestParam(required = false) LocalDate recDate,
            Pageable pageable )
            {

        return smartenergyRoomPulseCustomRepository.getSmartenergyRoomPulseList(buildingCode, roomCode, recDate);
    }

    //TanddRecordPulse
    @RequestMapping("/meteringLiveData/meteringLiveDataTanddList")
    public List<TanddRecordPulseCustomRepository.TanddRecordPulseViewModel> getMeteringDataTanddList(
            @RequestParam(required = false) String buildingCode,
            @RequestParam(required = false) String roomCode,
            @RequestParam(required = true) LocalDate recDate,
            Pageable pageable )
            {

        return tanddRecordPulseCustomRepository.getTanddRecordPulseList(buildingCode, roomCode, recDate);
    }

}
