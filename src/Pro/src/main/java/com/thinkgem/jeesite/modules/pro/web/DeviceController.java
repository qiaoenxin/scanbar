/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Device;
import com.thinkgem.jeesite.modules.pro.service.DeviceService;

/**
 * 生产计划Controller
 * @author Generate Tools
 * @version 2016-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/device")
public class DeviceController extends BaseController {

	@Autowired
	private DeviceService deviceService;
	
	@ModelAttribute
	public Device get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return deviceService.get(id);
		}else{
			return new Device();
		}
	}
	
	@RequiresPermissions("pro:device:view")
	@RequestMapping(value = {"list", ""})
	public String list(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			device.setCreateBy(user);
		}
        Page<Device> page = deviceService.find(new Page<Device>(request, response), device); 
        model.addAttribute("page", page);
        String url = "http://" +  request.getServerName() + ":" + request.getServerPort() +  request.getContextPath();
		model.addAttribute("clientUrl", url);
		return  "modules/pro/deviceList";
	}
	
	@RequiresPermissions("pro:device:view")
	@RequestMapping(value = "barcode")
	public String barcode(Device device, Model model, HttpServletRequest request) {
		model.addAttribute("device", device);
		String url = "http://" +  request.getServerName() + ":" + request.getServerPort() +  request.getContextPath();
		model.addAttribute("clientUrl", url);
		return "modules/pro/barcode";
	}

	@RequiresPermissions("pro:device:view")
	@RequestMapping(value = "form")
	public String form(Device device, Model model) {
		model.addAttribute("device", device);
		return "modules/pro/deviceForm";
	}

	@RequiresPermissions("pro:device:edit")
	@RequestMapping(value = "save")
	public String save(Device device, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, device)){
			return form(device, model);
		}
		device.setDeviceKey(deviceService.getDevice(device.getSerial()));
		if(!deviceService.valid(device)){
			addMessage(model, "无效的序列号");
			return form(device, model);
		}
		deviceService.save(device);
		addMessage(redirectAttributes, "保存设备号'" +device.getDeviceKey() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/pro/device/?repage";
	}
	
	@RequiresPermissions("pro:device:delete")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		deviceService.delete(id);
		addMessage(redirectAttributes, "删除设备号成功");
		return "redirect:"+Global.getAdminPath()+"/pro/device/?repage";
	}

}
