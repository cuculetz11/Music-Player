package commands;

import com.fasterxml.jackson.databind.JsonNode;
import detaliiPerUser.UsersPage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class SwitchVisibilityInput extends CommandInput{
    private int playlistId;

    public SwitchVisibilityInput(JsonNode node, String command) {
        this.setCommand(command);
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        this.playlistId = node.get("playlistId").asInt() - 1;
    }
    public String execute(UsersPage usersPage) {
        String message = "";
        if(usersPage == null) {
            message = "The specified playlist ID is too high.";
            return message;
        }
        if(this.playlistId < 0 || this.playlistId >= usersPage.getPlaylists().size()) {
            message = "The specified playlist ID is too high.";
            return message;
        } else {
            if(usersPage.getPlaylists().get(this.playlistId).isPublicVisibility()) {
                usersPage.getPlaylists().get(this.playlistId).setPublicVisibility(false);
                PublicPlaylists.getPublicPlaylists().remove(usersPage.getPlaylists().get(this.playlistId));
                message = "Visibility status updated successfully to private.";
            } else {
                usersPage.getPlaylists().get(this.playlistId).setPublicVisibility(true);
                PublicPlaylists.addPublicPlaylist(usersPage.getPlaylists().get(this.playlistId));
                message = "Visibility status updated successfully to public.";
            }
        }
        return message;
    }
}
