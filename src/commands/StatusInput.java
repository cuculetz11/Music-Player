package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import commandsResult.StatusOut;

public class StatusInput extends CommandInput{
    public StatusInput() {}

    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
    }
    public StatusOut execute(MusicPlayer player) {
        return new StatusOut(this.getCommand(), this.getUsername(), this.getTimestamp(),player);
    }
}
