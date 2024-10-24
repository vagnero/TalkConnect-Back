package com.talkconnect.controllers.ami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkconnect.services.ami.AsteriskAmiService;
@RestController
@RequestMapping("/api/ami")
public class AsteriskAmiController {
    @Autowired
    private AsteriskAmiService asteriskAMIService;

    @PostMapping("/command")
    public String runCommand(@RequestBody CommandRequest commandRequest) {
        return asteriskAMIService.sendCommand(commandRequest.getCommand());
    }
    
    public static class CommandRequest {
        private String command;

        // Getter e Setter
        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }
}