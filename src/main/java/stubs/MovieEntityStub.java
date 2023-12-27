package stubs;

import model.entity.MovieEntity;

public class MovieEntityStub {
    public static MovieEntity getNewMovieEntityStub(int identifier) {
        return MovieEntity.builder()
                .id(1_000_000 + identifier)
                .ref("http://1_000_000_" + identifier)
                .year(4400 + identifier)
                .title("Movie Title " + identifier)
                .build();
    }
}
