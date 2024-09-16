package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import commandsResult.PlayPauseOut;

public class PlayPauseInput extends CommandInput{
    public PlayPauseInput() {}
    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
    }
    public PlayPauseOut execute(MusicPlayer player) {
        PlayPauseOut out = new PlayPauseOut();
        out.setCommand(super.getCommand());
        out.setTimestamp(super.getTimestamp());
        out.setUser(super.getUsername());
        if(!player.isLoaded()) {
            out.setMessage("Please load a source before attempting to pause or resume playback.");
        } else if(player.isLoaded()) {
            if(!player.isPaused()) {
                player.setPaused(true);
                player.setLastTimestamp(-1);
                out.setMessage("Playback paused successfully.");
            } else if(player.isPaused() == true) {
                player.setPaused(false);
                player.setLastTimestamp(this.getTimestamp());
                out.setMessage("Playback resumed successfully.");
            }
        }
        return out;

    }
}
