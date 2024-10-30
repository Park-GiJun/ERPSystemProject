package com.gijun.erpproject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {
            "/",
            "/login",
            "/dashboard",
            "/personnel/**",
            "/salary/**",
            "/vacation/**",
            "/approval/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}