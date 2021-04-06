package com.eress.bootmaven.controller;

import com.eress.bootmaven.model.AccessDTO;
import com.eress.bootmaven.service.LoginService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class MainController {

    private final LoginService loginService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> index(HttpSession hs, Model model) {
        List<AccessDTO> resAccess = loginService.getAccessInfo();
        return new ResponseEntity<>("Info Request Success!!<br>" + resAccess.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/main.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(HttpSession hs, Model model) {
        if (hs.getAttribute("userId") == null) {
            return "redirect:/login/loginForm.do";
        } else {
            model.addAttribute("title", "메인");
            return "/main";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/mainTable.do", method = RequestMethod.POST)
    public String mainTable() {
        Gson gson = new Gson();
        Map<String, Object> obj = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("no", (i+1));
            temp.put("col1", "col1");
            temp.put("col2", "col2");
            temp.put("col3", "col3");
            temp.put("col4", "col4");
            temp.put("test1", "test1");
            temp.put("test2", "test2");
            list.add(temp);
        }
        obj.put("data", list);
        return gson.toJson(obj);
    }
}
