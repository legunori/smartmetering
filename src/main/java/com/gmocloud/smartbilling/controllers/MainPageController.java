package com.gmocloud.smartbilling.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gmocloud.smartbilling.dao.entities.LoginUserEntity;
import com.gmocloud.smartbilling.security.UserSession;

import lombok.extern.java.Log;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/08/19.
 */
@Log
@Controller
@SessionAttributes(value = "contactForm")
public class MainPageController {
	@RequestMapping("/main")
	public String get(@AuthenticationPrincipal UserSession user, Model model) {
		LoginUserEntity userEntity =  user.getUserEntity();

		String todo = "ＧＭＯクラウド"; //todo DBの会社プロフィールから取得

		model.addAttribute("companyName", todo);
//		model.addAttribute("userName", userEntity.getUserName());
		return "index";
	}

	@RequestMapping("/")
	public String home() {
		return "redirect:/main";
	}
}
