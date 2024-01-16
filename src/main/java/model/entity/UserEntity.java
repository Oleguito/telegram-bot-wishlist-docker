package model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
//    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_sequence")
    private long id;
    @Column(name = "username")
    private String username;

    @ManyToMany
    @JoinTable(name = "users_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<MovieEntity> movies;

}
