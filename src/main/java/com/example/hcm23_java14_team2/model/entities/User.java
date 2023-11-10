package com.example.hcm23_java14_team2.model.entities;


import com.example.hcm23_java14_team2.model.entities.Enum.StatusUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user")
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    //@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Email must not contain special characters or spaces!")
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //@Pattern(regexp = )
    @Length(min = 10, max = 11, message = "Phone must be min 10 and max 11 number")
    @Column(name = "phone")
    private String phone;

    @Column(name = "dayOfBirth")
    private String dateOfBirth;

    @Column(name = "gender")
    private char gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_user")
    private StatusUser statusUser = StatusUser.DEACTIVE;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private UserPermission userPermission;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Class_User> classUserList = new ArrayList();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userPermission.getRoleName().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
