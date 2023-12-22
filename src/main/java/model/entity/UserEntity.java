package model.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserEntity {
    private final int id;
    private final String username;
}
