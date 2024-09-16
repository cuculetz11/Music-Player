package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import commandsResult.ShuffleOut;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShuffleInput extends CommandInput {
    private int seed;
    public ShuffleInput() {}
    public void handleInput(JsonNode node, MusicPlayer player) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        if(node.has("seed")){
            seed = node.get("seed").asInt();
        }
    }
    public ShuffleOut execute(MusicPlayer player) {
        ShuffleOut out = new ShuffleOut();
        out.setCommand(this.getCommand());
        out.setUser(this.getUsername());
        out.setTimestamp(this.getTimestamp());
        if(!player.isLoaded()) {
            out.noSource();
            return out;
        }
        if(!player.getAudioCollection().isPlaylist()) {
            out.isNotPlaylist();
            return out;
        }
        if(player.isShuffle()) {
            player.setShuffle(false);
            player.setShuffleSeed(0);
            out.disabled();
            return out;
        }
        if(!player.isShuffle()) {
            player.setShuffle(true);
            player.setShuffleSeed(seed);
            out.activated();
            return out;
        }
        return out;
    }

}
