package com.talkconnect.services.friendship;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.talkconnect.models.entities.friendship.FriendShip;
import com.talkconnect.models.entities.friendship.FriendshipStatus;
import com.talkconnect.models.entities.user.User;
import com.talkconnect.repositories.FriendShipRepository;
import com.talkconnect.repositories.UserRepository;

@Component
public class FriendShipService {

    @Autowired
    FriendShipRepository repository;

    @Autowired
    UserRepository userRepository;
    
  public FriendShip insert(Long idUser, Long idFriend){
    User user = userRepository.getReferenceById(idUser);
    User friend = userRepository.getReferenceById(idFriend);
    var friendShip =
    FriendShip.builder()
    .createdAt(LocalDateTime.now())
    .status(FriendshipStatus.PENDING)
    .user(user)
    .friend(friend)
    .deleted(false)
    .requesterEmail(friend.getUsername())
    .build();
    FriendShip entity = repository.save(friendShip);
 
  
    return entity;
  }

//   public Set<FriendShip> findAllFriends(Long id){
//     User user = userRepository.getReferenceById(id);
//     Set<FriendShip> friends = repository.findAllFriends(user).get();
//     return friends;
//   } 



  public FriendShip acceptFriendship(Long userId, Long friendId) {
    // Encontrar a amizade entre os dois usuários
    User requester = userRepository.getReferenceById(userId); //Solicitante da amizade
    User addresses = userRepository.getReferenceById(friendId); //Quem foi solicitado

    FriendShip friendship = repository.findByUserAndFriend(addresses, requester);
    
    if (friendship.getStatus() == FriendshipStatus.PENDING && userId.equals(friendship.getFriend().getId())) {
      // Atualiza o status para ACCEPTED
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return repository.save(friendship);
    } else {
        throw new RuntimeException("Friendship request not in pending state");
    }
}



public FriendShip rejectFriendship(Long userId, Long friendId) {

      // Encontrar a amizade entre os dois usuários
      User requester = userRepository.getReferenceById(userId); //Solicitante da amizade
      User addresses = userRepository.getReferenceById(friendId); //Quem foi solicitado
  
  // Encontrar a amizade entre os dois usuários
  FriendShip friendship = repository.findByUserAndFriend(addresses, requester);  
  // Verifica se o status está PENDING
  if (friendship.getStatus() == FriendshipStatus.PENDING && userId.equals(friendship.getFriend().getId())) {
    // Atualiza o status para REJECTED
      friendship.setStatus(FriendshipStatus.REJECTED);
      return repository.save(friendship);
  } else {
      throw new RuntimeException("Friendship request not in pending state");
  }
}

}
