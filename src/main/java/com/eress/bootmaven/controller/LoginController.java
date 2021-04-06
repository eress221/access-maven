package com.eress.bootmaven.controller;

import com.eress.bootmaven.model.UserDTO;
import com.eress.bootmaven.service.LoginService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {

    private final LoginService loginService;

    @RequestMapping(value = "/loginForm.do", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("title", "로그인");
        return "/login/loginForm";
    }

    @ResponseBody
    @RequestMapping(value = "/loginCheck.do", method = RequestMethod.POST)
    public String loginCheck(@ModelAttribute UserDTO reqUser, HttpSession hs) {
        Gson gson = new Gson();
        Map<String, Object> obj = new HashMap<>();
        UserDTO resUser = loginService.getUserInfo(reqUser);
        String result = loginService.loginCheck(reqUser, resUser);
        obj.put("result", result);
        if ("lock".equals(result)) {
            obj.put("date", resUser.getFailDate());
        } else if ("diff".equals(result)) {
            obj.put("count", reqUser.getFailCnt());
        } else if ("success".equals(result)) {
            hs.setAttribute("userId", resUser.getUserId());
            hs.setAttribute("userNm", resUser.getUserNm());
        }

        // test
        if ("fail".equals(result)) {
            obj.put("result", "success");
            hs.setAttribute("userId", "test");
            hs.setAttribute("userNm", "테스트");
        }
        return gson.toJson(obj);
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public String logout(Model model, HttpSession hs) {
        hs.invalidate();
        model.addAttribute("title", "로그인");
        return "/login/loginForm";
    }
}
