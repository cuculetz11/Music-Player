package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.AudioCollection;
import commandPlayer.MusicPlayer;
import commandsResult.addRmvPlaylistResult;
import detaliiPerUser.PlayList;
import detaliiPerUser.UsersPage;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Setter
@Getter
public class addRemoveInPlaylistInput extends CommandInput{
    private int itemNumber;
    public addRemoveInPlaylistInput() {}

    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        this.itemNumber = node.get("playlistId").asInt() - 1;
    }
    public addRmvPlaylistResult execute(HashMap<String,UsersPage> usersPages, MusicPlayer player) {
        addRmvPlaylistResult result = new addRmvPlaylistResult();
        result.setCommand(this.getCommand());
        result.setUser(this.getUsername());
        result.setTimestamp(this.getTimestamp());
        if(!player.isLoaded()){
            result.setMessage("Please load a source before adding to or removing from the playlist.");
            return result;
        }
        AudioCollection audio = player.getAudioCollection();
         if(!audio.isSong()){
             result.setMessage("The loaded source is not a song.");
             return result;
         }
        SongInput song = audio.getSong();

        UsersPage usersPage = usersPages.get(super.getUsername());
         if(usersPage == null){
             result.setMessage("The specified playlist does not exist.");
             return result;
         }
        ArrayList<PlayList> playLists = usersPage.getPlaylists();

         if(itemNumber < 0 || itemNumber >= playLists.size()){
             result.setMessage("The specified playlist does not exist.");
             return result;
         }
         PlayList playList = playLists.get(itemNumber);

         if(playList.SongAfterName(song.getName())) {
             playList.removeSong(song.getName());
             result.setMessage("Successfully removed from playlist.");
         } else {
             playList.addSong(song);
             result.setMessage("Successfully added to playlist.");
         }
        return result;


    }
}
