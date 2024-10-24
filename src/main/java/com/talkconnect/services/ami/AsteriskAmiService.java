package com.talkconnect.services.ami;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.CommandAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class AsteriskAmiService {
    @Value("${domain.url}")
    private String domainUrl;

    @Value("${ip.url}")
    private String ipUrl;

    private ManagerConnection managerConnection;

    @Autowired
    private AsteriskEventListener asteriskEventListener;

    private final String host = domainUrl;  // Host do Asterisk
    private final String username = "admin";  // Usuário AMI
    private final String password = "1234";  // Senha AMI

    @PostConstruct
    public void init() {
        try {
            ManagerConnectionFactory factory = new ManagerConnectionFactory(host, username, password);
            this.managerConnection = factory.createManagerConnection();
            managerConnection.addEventListener(asteriskEventListener);  // Adiciona o listener
            managerConnection.login();

            // Registrar log de sucesso na conexão
            System.out.println("Conectado ao AMI do Asterisk");

        } catch (AuthenticationFailedException | TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao conectar com AMI: " + e.getMessage());
        }
    }

    public String sendCommand(String command) {
        try {
            // Envia um comando AMI para o Asterisk
            CommandAction action = new CommandAction(command);
            ManagerResponse response = managerConnection.sendAction(action);
            return response.getMessage();  // Retorna a resposta do comando
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao executar o comando: " + e.getMessage();
        }
    }
}