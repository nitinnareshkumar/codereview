package com.review.code.RapidDev;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    private String message="HOLA";
    @GetMapping("hello")
    public String main(Model model) {
        model.addAttribute("message", message);
        //model.addAttribute("tasks", tasks);

        return "welcome"; //view
    }
}
