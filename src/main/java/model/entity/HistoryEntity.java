package model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Getter
@ToString
public class HistoryEntity {

    private long id;
    private long user_id;
    private String command;
    private Timestamp operation_time;

}
