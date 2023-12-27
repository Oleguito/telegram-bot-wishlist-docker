package parsers;

import model.entity.MovieEntity;

public interface Parser {
    MovieEntity parse(String reference);
}
