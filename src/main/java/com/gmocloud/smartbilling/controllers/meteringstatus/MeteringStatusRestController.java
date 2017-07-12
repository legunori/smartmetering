package com.gmocloud.smartbilling.controllers.meteringstatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmocloud.smartbilling.dao.customRepositories.MeteringStatusCustomRepository;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@RestController
public class MeteringStatusRestController {

//    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Autowired
    private MeteringStatusCustomRepository meteringStatusCustomRepository;

    //検針状況検索
    @RequestMapping("/meteringStatus/meteringStatusList")
    public List<MeteringStatusCustomRepository.MeteringStatusListViewModel> getMeteringStatusList(
            @RequestParam(required = false) String buildingCode,
            @RequestParam(required = false) String status,
            Pageable pageable )
            {

        return meteringStatusCustomRepository.getMeteringStatusList(buildingCode, status);
    }

}
