package com.talkconnect.services.ami;

import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.ConfbridgeJoinEvent;
import org.asteriskjava.manager.event.ConfbridgeLeaveEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.springframework.stereotype.Component;

@Component
public class AsteriskEventListener implements ManagerEventListener {

    @Override
    public void onManagerEvent(ManagerEvent event) {
        System.out.println("Event received: " + event);
    }
}