package com.example.scoreboard.entites;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "2-20 letters")
    private String nickname;

    @Column(nullable = false)
    @NotEmpty(message = "not empty")
    @Email(message = "it is not email")
    private String email;

    @Column
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#/|?!@$%^&*-_,.:;'%()<>{}]).{6,}$", message = " must contain 0-9, a-z, A-Z, special character, length 6-30")
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<HockeyBoard> boardList;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;

//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;
//@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
//    private Role roles;


//    public User(String email, String password) {
//        this.email = email;
//        this.password = password;
//        roles.add(new Role());
//    }
}
