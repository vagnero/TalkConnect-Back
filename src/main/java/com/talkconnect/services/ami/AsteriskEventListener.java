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
        if (event instanceof NewChannelEvent) {
            NewChannelEvent newChannelEvent = (NewChannelEvent) event;
            System.out.println("Novo canal criado: " +
                newChannelEvent.getChannel() + " - Origem: " + newChannelEvent.getCallerIdNum());
        } else if (event instanceof HangupEvent) {
            HangupEvent hangupEvent = (HangupEvent) event;
            System.out.println("Chamada encerrada: " +
                hangupEvent.getCallerIdNum() + " - Canal: " + hangupEvent.getChannel());
        } else if (event instanceof DialEvent) {
            DialEvent dialEvent = (DialEvent) event;
            System.out.println("Ligação de: " +
                dialEvent.getCallerIdNum() + " para: " + dialEvent.getDestination());
        } else if (event instanceof ConfbridgeJoinEvent) {
            ConfbridgeJoinEvent confbridgeJoinEvent = (ConfbridgeJoinEvent) event;
            System.out.println("Participante entrou na conferência: " +
                confbridgeJoinEvent.getCallerIdNum() + " - Conferência: " + confbridgeJoinEvent.getConference());
        } else if (event instanceof ConfbridgeLeaveEvent) {
            ConfbridgeLeaveEvent confbridgeLeaveEvent = (ConfbridgeLeaveEvent) event;
            System.out.println("Participante saiu da conferência: " +
                confbridgeLeaveEvent.getCallerIdNum() + " - Conferência: " + confbridgeLeaveEvent.getConference());
        } else {
            // Para outros eventos, você pode logar ou ignorar
            System.out.println("Evento recebido: " + event.getClass().getSimpleName());
        }
    }
}