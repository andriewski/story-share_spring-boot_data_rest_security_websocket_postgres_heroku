package com.storyshare.boot.pojos;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable {
    @Id
    @Min(1L)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column
    //@Type(type = "string")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Access(AccessType.PROPERTY)
    @Length(min = 1, max = 1000)
    private String text;

    @Column/*(columnDefinition = "DATETIME(3)")*/
    @Access(AccessType.PROPERTY)
    private LocalDateTime date;

    @Column(name = "DELETED_BY_SENDER", columnDefinition = "BOOLEAN DEFAULT 'FALSE'")
    @Type(type = "boolean")
    @Access(AccessType.PROPERTY)
    private Boolean deletedBySender;

    @Column(name = "DELETED_BY_RECEIVER", columnDefinition = "BOOLEAN DEFAULT 'FALSE'")
    @Type(type = "boolean")
    @Access(AccessType.PROPERTY)
    private Boolean deletedByReceiver;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SENDER_ID", foreignKey = @ForeignKey(name = "SENDER_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User sender;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RECEIVER_ID", foreignKey = @ForeignKey(name = "RECEIVER_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User receiver;

    public Message(String text, LocalDateTime date, User sender, User receiver) {
        this.text = text;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.deletedByReceiver = false;
        this.deletedBySender = false;
    }
}
