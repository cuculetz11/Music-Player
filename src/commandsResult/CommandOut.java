package commandsResult;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandOut {
    private String command;
    private String user;
    private int timestamp;
    private String message;


    public CommandOut(String command, String user, int timestamp, String message) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
    }
    public CommandOut() {}
}
