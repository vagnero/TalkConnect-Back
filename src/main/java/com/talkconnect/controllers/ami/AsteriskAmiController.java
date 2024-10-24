package com.talkconnect.controllers.ami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkconnect.services.ami.AsteriskAmiService;

@RestController
@RequestMapping("/api/ami")
public class AsteriskAmiController {
    @Autowired
    private AsteriskAmiService asteriskAMIService;

    @GetMapping("/command")
    public String runCommand(@RequestParam String command) {
        return asteriskAMIService.sendCommand(command);
    }
    
}
