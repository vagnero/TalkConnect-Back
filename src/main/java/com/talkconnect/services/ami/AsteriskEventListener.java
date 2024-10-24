package com.talkconnect.services.ami;

import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.ManagerEvent;
import org.springframework.stereotype.Component;

@Component
public class AsteriskEventListener implements ManagerEventListener {

    @Override
    public void onManagerEvent(ManagerEvent event) {
        // Aqui você pode processar e logar os eventos do Asterisk
        System.out.println("Evento recebido: " + event);
    }
}