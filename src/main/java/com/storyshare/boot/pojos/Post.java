package com.storyshare.boot.pojos;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Post implements Serializable {
    @Id
    @Min(1L)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    @Length(min = 4, max = 2000)
    private String text;

    @Column/*(columnDefinition = "DATETIME(3)")*/
    @Access(AccessType.PROPERTY)
    private LocalDateTime date;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    @Pattern(regexp = "^.+(\\.jpg|\\.png|\\.jpeg|\\.gif)$")
    private String picture;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "USER_POST_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "LIKE",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<User> likes;

    public Post(String text, LocalDateTime date, User user, String picture) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.picture = picture;
        this.likes = new ArrayList<>();
    }
}
