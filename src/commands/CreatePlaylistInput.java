package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import commandsResult.PlaylistOut;
import detaliiPerUser.PlayList;
import detaliiPerUser.UsersPage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class CreatePlaylistInput extends CommandInput{
    private String playlistName;
    public CreatePlaylistInput() {
    }

    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        this.playlistName = node.get("playlistName").asText();

    }
    public PlaylistOut execute (HashMap<String, UsersPage> userspages) {
        PlaylistOut playlistout = new PlaylistOut();
        playlistout.setCommand(this.getCommand());
        playlistout.setUser(this.getUsername());
        playlistout.setTimestamp(this.getTimestamp());
        UsersPage usersPage = userspages.get(super.getUsername());
        if(usersPage == null) {
            usersPage = new UsersPage();
            PlayList playList = new PlayList(playlistName,this.getUsername(),this.getTimestamp());
            usersPage.createPlaylist(playList);
            userspages.put(super.getUsername(), usersPage);
            playlistout.setMessage("Playlist created successfully.");
            return playlistout;
        }
        if(usersPage.playlistWithThatName(playlistName)) {
            playlistout.setMessage("A playlist with the same name already exists.");
            return playlistout;
            }

        PlayList playList = new PlayList(playlistName,this.getUsername(),this.getTimestamp());
        usersPage.createPlaylist(playList);
        playlistout.setMessage("Playlist created successfully.");
        return playlistout;
    }
}
