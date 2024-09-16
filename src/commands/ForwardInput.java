package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;

public class ForwardInput extends CommandInput {
    public ForwardInput(JsonNode node, String command) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        super.setCommand(command);
    }
    public String execute(MusicPlayer player) {
        String message = "";
        if(!player.isLoaded()) {
            message = "Please load a source before attempting to forward.";
            return message;
        } else {
            if(!player.getAudioCollection().isPodcast()) {
                message="The loaded source is not a podcast.";
                return message;
            }
            if(player.getRemainedTime() > 90) {
                player.setTimePlayed(player.getTimePlayed() + 90);
                player.setRemainedTime(player.getRemainedTime() - 90);
            } else {
                if(player.getCurrentEpisode() + 1 == player.getAudioCollection().getPodcast().getEpisodes().size()) {
                    player.resetAll();
                    message = "Please load a source before attempting to forward.";
                    return message;
                }
                player.setCurrentEpisode(player.getCurrentEpisode() + 1);
                player.setTimePlayed(0);
                player.setAudioTime(player.getAudioCollection().getPodcast().getEpisodes().get(player.getCurrentEpisode()).getDuration());
                player.setRemainedTime(player.getAudioCollection().getPodcast().getEpisodes().get(player.getCurrentEpisode()).getDuration());
            }
            message = "Skipped forward successfully.";
            return message;

        }
    }
}
