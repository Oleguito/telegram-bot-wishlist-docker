package parsers;

import model.entity.MovieEntity;

public interface ParserRepo {
    MovieEntity parse(String reference);
}
