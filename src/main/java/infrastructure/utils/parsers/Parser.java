package infrastructure.utils.parsers;

import domain.model.entity.MovieEntity;

public interface Parser {
    MovieEntity parse(String reference);
}
