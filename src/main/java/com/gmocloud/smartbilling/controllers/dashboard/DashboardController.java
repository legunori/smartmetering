package com.gmocloud.smartbilling.controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/19.
 */
@Controller
public class DashboardController {
    @RequestMapping("/dashboard")
    public String get() {
        return "views/dashboard";
    }
}
