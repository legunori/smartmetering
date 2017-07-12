package com.gmocloud.smartbilling.controllers.meteringlivedata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@Controller
public class MeteringLiveDataController {
    @RequestMapping("/meteringLiveDataSmartenergy")
    public String getMeteringLiveDataSmartenergy() {
        return "views/metering_live_data_smartenergy";
    }
    @RequestMapping("/meteringLiveDataTandd")
    public String getMeteringLiveDataTandd() {
        return "views/metering_live_data_tandd";
    }
}
