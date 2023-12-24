package model.entity;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MovieEntity {
    private final int id;
    private final String ref;
    private final String title;
    private final Integer year;
//    private final byte[] picture;
}
