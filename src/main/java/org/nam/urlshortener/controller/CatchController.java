package org.nam.urlshortener.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CatchController {
    @Value("${frontend.url}")
    private String frontendUrl;


    @GetMapping("/")
    public RedirectView redirectToFrontend() {
        return new RedirectView(frontendUrl);
    }

    @GetMapping(value = {"/robots.txt", "robot.txt"})
    @ResponseBody
    public String getRobotsTxt() {
        return "User-agent: *\n" +
                "Disallow: /admin\n";
    }

    @GetMapping(value = {"/favicon.ico"})
    @ResponseBody
    public String noFavicon() {
        return "";
    }
}
