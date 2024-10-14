package com.talkconnect.models.entities.friendship;

import com.talkconnect.models.entities.BaseEntity;
import com.talkconnect.models.entities.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "friendships", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "friend_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FriendShip extends BaseEntity {
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private User friend;

    @Column(name = "requester_email", nullable= false)
    private String requesterEmail;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status; // PENDING, ACCEPTED, REJECTED
    
}
