package com.example.hcm23_java14_team2.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String token;

    @Column()
    private LocalDateTime createdAt;

    @Column()
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}