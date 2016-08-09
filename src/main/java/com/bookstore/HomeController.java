package com.bookstore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by irtza on 8/5/16.
 */
@Controller
public class HomeController {



    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

}
