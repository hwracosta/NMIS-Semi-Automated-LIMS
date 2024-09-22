package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientTrackReqController {
    @GetMapping("/CLIENT-trackreq")
    public String clientTrackReq() {
        return "CLIENT-trackreq";
    }
}