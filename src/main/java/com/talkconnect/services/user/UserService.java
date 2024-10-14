package com.talkconnect.services.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkconnect.models.entities.friendship.FriendShip;
import com.talkconnect.models.entities.friendship.FriendshipStatus;
import com.talkconnect.models.entities.message.Message;
import com.talkconnect.models.entities.user.User;
import com.talkconnect.repositories.FriendShipRepository;
import com.talkconnect.repositories.MessageRepository;
import com.talkconnect.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    FriendShipRepository friendShipRepository;

    @Autowired
    MessageRepository messageRepository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public User insert(User obj) {
       
        return repository.save(obj);
    }

    public User update (Long id, User obj) {
        User entity = repository.getReferenceById(id);
        updateData(entity, obj);
        return repository.save(entity);
    }

    public void updateData(User entity, User user){
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setUpdatedAt(LocalDateTime.now());
    }


    //FindAllFriends retorna todos os amigos que te aceitaram

    public List<User> findAllFriends(Long id) {
        User user = repository.findById(id).orElseThrow();
        
        // Combina as duas listas (solicitações enviadas e recebidas)
        List<User> allFriends = new ArrayList<>();
        // Adiciona amigos das solicitações enviadas
        allFriends.addAll(user.getReceivedFriendRequests()
        .stream()
        .filter(friendship -> friendship.getStatus() == FriendshipStatus.ACCEPTED)
        .map(FriendShip::getFriend) // Supondo que getRequested retorna o User que recebeu a solicitação
        .collect(Collectors.toList()));

        // Adiciona amigos das solicitações recebidas
        allFriends.addAll(user.getSentFriendRequests()
        .stream()
        .filter(friendship -> friendship.getStatus() == FriendshipStatus.ACCEPTED)
        .map(FriendShip::getUser) // Supondo que getSender retorna o User que enviou a solicitação
        .collect(Collectors.toList()));
    
        // Remove o usuário que está fazendo a solicitação da lista
        allFriends = allFriends.stream()
        .filter(friend -> !friend.getId().equals(id)) // Exclui o próprio usuário
        .distinct() // Remove duplicatas, se houver
        .collect(Collectors.toList());
        return allFriends;
    }

    public List<User> getAllUserLessYou(Long id) {
        return repository.findAll()
        .stream()
        .filter(users -> !(users.getId().equals(id)))
        .toList()
        ;
    }

    public List<Message> allUserMessages(Long userId, Long friendId) {
        User user = repository.getReferenceById(userId);
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(user.getSentMessages());
        allMessages.addAll(user.getReceivedMessages());
        allMessages.stream()
        .filter(userMessages -> userMessages.getReceiver().equals(friendId) //Filtra pelo id do amigo
         && userMessages.getSender().getId().equals(userId)) //Filtra pelo id do usuário
         ;
        return allMessages;
    }

    public List<User> findReceivedPendingFriends(Long id){
        User user = repository.findById(id).orElseThrow();
        
        // Combina as duas listas (solicitações enviadas e recebidas)
        List<User> allFriends = new ArrayList<>();
        // Adiciona amigos das solicitações enviadas
        // allFriends.addAll(user.getReceivedFriendRequests()
        // .stream()
        // .filter(friendship -> friendship.getStatus() == FriendshipStatus.PENDING)
        // .map(FriendShip::getFriend) // Supondo que getRequested retorna o User que recebeu a solicitação
        // .collect(Collectors.toList()));

        // Adiciona amigos das solicitações recebidas
        allFriends.addAll(user.getSentFriendRequests()
        .stream()
        .filter(friendship -> friendship.getStatus() == FriendshipStatus.PENDING)
        .map(FriendShip::getUser) // Supondo que getSender retorna o User que enviou a solicitação
        .collect(Collectors.toList()));
    
        // Remove o usuário que está fazendo a solicitação da lista
        allFriends = allFriends.stream()
        .filter(friend -> !friend.getId().equals(id)) // Exclui o próprio usuário
        .distinct() // Remove duplicatas, se houver
        .collect(Collectors.toList());
        return allFriends;
    }

    
    
}
