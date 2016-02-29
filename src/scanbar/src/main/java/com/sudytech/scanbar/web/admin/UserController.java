package com.sudytech.scanbar.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sudytech.scanbar.bean.User;
import com.sudytech.scanbar.service.UserService;
import com.sudytech.scanbar.util.StringUtils;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute()
	public User getId(String id){
		if (StringUtils.isEmpty(id)){
			return new User();
		}else{
			return userService.findById(StringUtils.toInt(id, 0));
		}
	}
	
	
	@RequestMapping("save")
	public String save(User user, Model model, RedirectAttributes redirectAttributes){
		userService.save(user);
		return null;
	}
	
	@RequestMapping("delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		
		return null;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
