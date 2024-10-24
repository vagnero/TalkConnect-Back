package com.talkconnect.services.ami;

import java.lang.reflect.Field;

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
        // Exibe os campos não nulos do evento
        System.out.println("Evento recebido: " + formatEvent(event));
    }

    // Método para filtrar e exibir apenas os campos não nulos
    private String formatEvent(ManagerEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(event.getClass().getSimpleName()).append(" [");
        
        // Usa reflexão para acessar os campos do evento
        Field[] fields = event.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);  // Permite acessar campos privados
            try {
                Object value = field.get(event);
                if (value != null) {
                    sb.append(field.getName()).append("='").append(value).append("', ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Remove a última vírgula e espaço, se houver
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");

        return sb.toString();
    }
}