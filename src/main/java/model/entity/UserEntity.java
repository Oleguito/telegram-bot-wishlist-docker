package model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<MovieEntity> movies;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(movies, that.movies);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, username, movies);
    }
}
