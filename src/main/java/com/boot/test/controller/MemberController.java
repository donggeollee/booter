package com.boot.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
public class MemberController {

    @PostMapping("/create")
    public Object createUser(){


        return "asdfasf";
    }
}
