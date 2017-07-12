package com.gmocloud.smartbilling.controllers.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/09/22.
 */

@Controller
public class CommonController {
    @RequestMapping("parts/pagination")
    public String get() {
        return "parts/part_pagination";
    }
}
