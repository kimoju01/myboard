package com.hyeju.study.myboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("destination-single")
    public String destination_single() {
        return "destination-single";
    }

    @GetMapping("hotel")
    public String hotel() {
        return "hotel";
    }

    @GetMapping("hotel-single")
    public String hotel_single() {
        return "hotel-single";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }



}
