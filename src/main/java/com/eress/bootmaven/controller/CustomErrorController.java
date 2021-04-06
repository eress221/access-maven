package com.eress.bootmaven.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value="/error")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("status", String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)));
        model.addAttribute("message", String.valueOf(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)));
        model.addAttribute("exception", String.valueOf(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)));
        return "/common/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
