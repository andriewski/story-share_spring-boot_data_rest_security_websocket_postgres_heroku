package com.storyshare.boot.pojos;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cache;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {
    @Id
    @Min(1L)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    @Length(min = 1, max = 25)
    private String name;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    @Pattern(regexp = "^.+(\\.jpg|\\.png|\\.jpeg|\\.gif)$")
    private String avatar;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    @Length(min = 1, max = 100)
    private String password;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'user'")
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String role;

    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'active'")
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String status;

    @OneToMany(
            mappedBy = "sender",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Message> senderMessages = new ArrayList<>();

    @OneToMany(
            mappedBy = "receiver",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Message> receiverMessages = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    @Version
    private Integer version;

    public User(String name, String email, String avatar, String password, String role) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.role = role;
        this.status = "active";
    }
}
