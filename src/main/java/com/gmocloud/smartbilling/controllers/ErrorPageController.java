package com.gmocloud.smartbilling.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/23.
 */
@Controller
public class ErrorPageController{
    @RequestMapping("/404")
    String notFoundError() {
        return "errors/error-404-alt";
    }
}
