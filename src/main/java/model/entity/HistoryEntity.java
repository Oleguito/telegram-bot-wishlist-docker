package model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.grizzly.http.util.TimeStamp;

@Builder
@Getter
@Setter
public class HistoryEntity {

    private int id;
    private int user_id;
    private String command;
    private TimeStamp operation_time;

}
