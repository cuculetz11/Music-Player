package commandsResult;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;

public class RepeatOut extends CommandOut{
    public RepeatOut(JsonNode node, String command) {
        super.setCommand(command);
        super.setTimestamp(node.get("timestamp").asInt());
        super.setUser(node.get("username").asText());
    }
    public void execute(MusicPlayer player) {
        if(!player.isLoaded()) {
            super.setMessage("Please load a source before setting the repeat status.");
        } else {
            int repeatStatus = player.getRepeat();
            if(repeatStatus == 0) {
                player.setRepeat(1);
                if(player.getAudioCollection().isPlaylist()) {
                    super.setMessage("Repeat mode changed to repeat all.");
                } else {
                    super.setMessage("Repeat mode changed to repeat once.");
                }
            } else if(repeatStatus == 1) {
                player.setRepeat(2);
                if(player.getAudioCollection().isPlaylist()) {
                    super.setMessage("Repeat mode changed to repeat current song.");
                } else {
                    super.setMessage("Repeat mode changed to repeat infinite.");
                }
            } else if(repeatStatus == 2) {
                player.setRepeat(0);
                super.setMessage("Repeat mode changed to no repeat.");
            }
        }
    }
}
