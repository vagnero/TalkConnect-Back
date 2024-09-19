package com.talkconnect.models.entities.roles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.talkconnect.models.entities.roles.Permission.ADMIN_CREATE;
import static com.talkconnect.models.entities.roles.Permission.ADMIN_DELETE;
import static com.talkconnect.models.entities.roles.Permission.ADMIN_READ;
import static com.talkconnect.models.entities.roles.Permission.ADMIN_UPDATE;
import static com.talkconnect.models.entities.roles.Permission.CLIENT_CREATE;
import static com.talkconnect.models.entities.roles.Permission.CLIENT_DELETE;
import static com.talkconnect.models.entities.roles.Permission.CLIENT_READ;
import static com.talkconnect.models.entities.roles.Permission.CLIENT_UPDATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Role {
    //Define o conjunto de permissões das roles ADMIN e USER_
    ADMIN(
        Set.of(
           ADMIN_CREATE,
           ADMIN_READ,
           ADMIN_UPDATE,
           ADMIN_DELETE

        )
    ),
    CLIENT(
        Set.of(
            CLIENT_CREATE,
            CLIENT_READ,
            CLIENT_UPDATE,
            CLIENT_DELETE
        )
    ),
    ;
    
    //Gera o método getter para o campo permissions
    @Getter
    private final Set<Permission> permissions; //Set é uma lista que não contém elementos duplicados de permissões de Permission.java

    //Método para determinar quais permissões cada role tem, retorna uma lista de objetos SimpleGrantedAuthority
    public List<SimpleGrantedAuthority> getAuthorities(){

        var authorities = getPermissions() //Variável que conterá todas as permissões/roles
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission())) //Cria um SimpleGrantedAuthority para cada permission
        .collect(Collectors.toList()); //Coleta os objetos SimpleGrantedAuthority gerados pelo map() em uma lista
        

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); // atribui a lista acima a variável authorities.
        return authorities;
    }
}
