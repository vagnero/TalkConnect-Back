package com.talkconnect.models.entities.message;

import com.talkconnect.models.entities.BaseEntity;
import com.talkconnect.models.entities.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Message extends BaseEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead;


}