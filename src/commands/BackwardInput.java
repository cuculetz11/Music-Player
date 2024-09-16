package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;

public class BackwardInput extends CommandInput{
    public BackwardInput(JsonNode node, String command) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        super.setCommand(command);
    }
    public String execute(MusicPlayer player) {
        String message = "";
        if (!player.isLoaded()) {
            message = "Please load a source before skipping backward.";
            return message;
        } else {
            if (!player.getAudioCollection().isPodcast()) {
                message = "The loaded source is not a podcast.";
                return message;
            }
            if (player.getTimePlayed() > 90) {
                player.setTimePlayed(player.getTimePlayed() - 90);
                player.setRemainedTime(player.getRemainedTime() + 90);
                message = "Rewound successfully.";
                return message;
            } else {
                if (player.getCurrentEpisode() == 0) {
                    player.setTimePlayed(0);
                    player.setRemainedTime(player.getAudioTime());
                    message = "Rewound successfully.";
                    return message;
                } else {
                    player.setCurrentEpisode(player.getCurrentEpisode() - 1);
                    player.setTimePlayed(player.getAudioTime());
                    player.setRemainedTime(player.getAudioTime());
                    message = "Rewound successfully.";
                    return message;
                }
            }
        }
    }

}
