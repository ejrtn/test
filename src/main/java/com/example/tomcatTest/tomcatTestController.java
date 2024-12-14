package com.example.tomcatTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class tomcatTestController {

    @GetMapping("/")
    public String index(){
        return "test";
    }
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "<h1>test</h1>";
    }
}
