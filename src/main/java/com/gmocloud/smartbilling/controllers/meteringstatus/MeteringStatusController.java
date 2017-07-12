package com.gmocloud.smartbilling.controllers.meteringstatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@Controller
public class MeteringStatusController {
    @RequestMapping("/meteringStatus")
    public String getMeteringStatus() {
        return "views/metering_status";
    }
}
