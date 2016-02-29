package com.sudytech.scanbar.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sudytech.scanbar.bean.User;
import com.sudytech.scanbar.util.Config;

@RequestMapping("/login")
@Controller
public class LoginController {
	
	
	@RequestMapping(value="/auth")
	public void auth(User game, Model model){
		String value = Config.getProperty("web.view.index");
		System.out.println(value);
	}
	
	@ResponseBody
	@RequestMapping("/json")
	public void json(){
		System.out.println("doLogin");
	}
	
}
