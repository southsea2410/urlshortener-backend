package org.nam.urlshortener.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CatchController {
    @Value("${frontend.url}")
    private String frontendUrl;


    @GetMapping("/")
    public RedirectView redirectToFrontend() {
        return new RedirectView(frontendUrl);
    }
}
