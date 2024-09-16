package commands;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Stats {
    private String name;
    private int remainedTime;
    private String repeat;
    private boolean shuffle;
    private boolean paused;
    public Stats(String name, int remainedTime, String repeat, boolean shuffle, boolean paused) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.repeat = repeat;
        this.shuffle = shuffle;
        this.paused = paused;

    }
    public Stats() {
        this("", 0, "No Repeat", false, true);
    }

}
