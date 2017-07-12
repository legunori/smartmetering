package com.gmocloud.smartbilling.controllers.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/04.
 */
@Controller
public class SystemController {
    @RequestMapping("/system")
    public String get() {
        return "views/system";
    }
}
