package model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class HistoryEntity {

    private int id;
    private int user_id;
    private String command;
    private Timestamp operation_time;

}
