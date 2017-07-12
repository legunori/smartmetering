package com.gmocloud.smartbilling.controllers.dashboard;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmocloud.smartbilling.controllers.dashboard.models.MeteringStatusJsonModel;
import com.gmocloud.smartbilling.dao.customRepositories.MeteringStatusCustomRepository;
import com.gmocloud.smartbilling.dao.customRepositories.MeteringStatusCustomRepository.MeteringStatusCountViewModel;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/19.
 */

@RestController
@Log
public class DashboardRestController {

    @Autowired
    MeteringStatusCustomRepository meteringStatusCustomRepository;

    /**
     * Dashboard 検針状況
     */
    @RequestMapping(value = "/dashboard/meteringStatus")
    public List<MeteringStatusJsonModel> getMeteringStatus() {

        List<MeteringStatusJsonModel> jsonArray = new ArrayList<>();
        List<MeteringStatusCountViewModel> countViewModel =meteringStatusCustomRepository.getMeteringStatusCount();

        for(MeteringStatusCountViewModel countView : countViewModel){
            MeteringStatusJsonModel jsonModel = new MeteringStatusJsonModel();
            jsonModel.setBuildingCode(countView.getBuildingCode());
            jsonModel.setBuildingName(countView.getBuildingName());
            jsonModel.setRunningCount(countView.getRunningCount());
            jsonModel.setWarningCount(countView.getWarningCount());
            jsonModel.setStoppingCount(countView.getStoppingCount());

            jsonArray.add(jsonModel);
        }
        return jsonArray;

    }

}
