package commands;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandInput {
    private String command;
    private String username;
    private int timestamp;

    public CommandInput() {}

}
