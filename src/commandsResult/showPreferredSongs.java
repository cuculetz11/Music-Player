package commandsResult;

import com.fasterxml.jackson.databind.JsonNode;
import fileio.input.SongInput;

import java.util.ArrayList;

public class showPreferredSongs {
    private String command;
    private String user;
    private int timestamp;
    private ArrayList<String> result;

    public showPreferredSongs(String command, JsonNode node) {
        this.command = command;
        this.user = node.get("username").asText();
        this.timestamp = node.get("timestamp").asInt();
        this.result = new ArrayList<>();
    }

    public void addToResult(ArrayList<SongInput> likedSongs) {
        for (SongInput song : likedSongs) {
            result.add(song.getName());
        }
    }
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
