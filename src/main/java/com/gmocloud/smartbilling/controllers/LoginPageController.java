package com.gmocloud.smartbilling.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/22.
 */
@Controller
public class LoginPageController {
    //ログインページ
    @RequestMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error) {
        //エラーコードは認証エラー時に、LoginFailureHandlerクラスでセットされる
        model.addAttribute("error_code", error);
        return "login";
    }

    //パスワードリセットページ
    @RequestMapping(value = "/reset")
    public String reset() {
        return "reset";
    }

    //パスワードリセットページ
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String resetPOST(Model model) {
        int status = 1;//TODO 1:メール送信OK 2:メールアドレス存在しない 3:サーバーエラー
        model.addAttribute("status", status);
        return "reset";
    }
}
