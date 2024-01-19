package model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movies")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "movies_seq_gen", sequenceName = "movies_sequence")
    private long id;
    private String ref;
    private String title;
    private Integer year;
    
    @ManyToMany(mappedBy = "movies")
    private List <UserEntity> users;
    
//    private final byte[] picture;
    
    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        MovieEntity that = (MovieEntity) o;
        return id == that.id &&
                Objects.equals(ref, that.ref) &&
                Objects.equals(title, that.title) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref, title, year);
    }
}
