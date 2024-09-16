package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;

public class FollowInput extends CommandInput{
    public FollowInput(JsonNode node, String command) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        super.setCommand(command);
    }
    public String execute() {
        String message = "";
        if(!Selected.isIsSelected()) {
            message = "Please select a source before following or unfollowing.";
            return message;
        } else {
            if(!Selected.isTherePlaylistSelected()) {
                message = "The selected source is not a playlist.";
                return message;
            }
            if(Selected.getSelectedPlaylist().getOwner().equals(getUsername())) {
                message = "You cannot follow or unfollow your own playlist.";
                return message;
            }
            if(Selected.getSelectedPlaylist().isAFollower(getUsername())) {
                Selected.getSelectedPlaylist().removeFollower(getUsername());
                message = "Playlist unfollowed successfully.";
                return message;
            } else {
                Selected.getSelectedPlaylist().addFollower(getUsername());
                message = "Playlist followed successfully.";
                return message;
            }
        }
    }
}
