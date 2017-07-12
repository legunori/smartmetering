package com.gmocloud.smartbilling.controllers.meterresult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@Controller
public class MeterResultController {
    @RequestMapping("/meterResultDaily")
    public String getResultDaily() {
        return "views/meter_result_daily";
    }
    @RequestMapping("/meterResultMonthly")
    public String getResultMonthly() {
        return "views/meter_result_monthly";
    }
}
