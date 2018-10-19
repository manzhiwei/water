package com.welltech.controller.page;

import org.apache.log4j.Logger;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DashboardPageController {

    private static Logger logger = Logger
            .getLogger(DashboardPageController.class);

    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response,Device device,Model model) {

        String type=request.getParameter("type");
        if("vip".equals(type)){
            model.addAttribute("vip", "selected");
        }else if("alarm".equals(type)){
            model.addAttribute("alarm", "selected");
        }else{
            type="raw";
            model.addAttribute("raw", "selected");
        }
        model.addAttribute("type",type);
        model.addAttribute("dashboard","active");
        return "dashboard/index";
    }

}
