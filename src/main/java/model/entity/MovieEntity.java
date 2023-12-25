package model.entity;


import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class MovieEntity {
    private final long id;
    private final String ref;
    private final String title;
    private final Integer year;
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
