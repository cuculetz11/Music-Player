package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import commandsResult.LikeOut;
import detaliiPerUser.GeneralStatistics;
import detaliiPerUser.SongLikeInfo;
import detaliiPerUser.UsersPage;
import fileio.input.LibraryInput;
import fileio.input.SongInput;

import java.util.HashMap;

public class LikeInput extends CommandInput{
    public LikeInput() {}
    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
    }

    public LikeOut execute(HashMap<String, UsersPage> usersPages, MusicPlayer player) {
        LikeOut likeOut = new LikeOut();
        likeOut.setCommand(super.getCommand());
        likeOut.setUser(super.getUsername());
        likeOut.setTimestamp(super.getTimestamp());
        if(!player.isLoaded()) {
            likeOut.noSource();
            return likeOut;
        }
        if(player.getAudioCollection().isPodcast()) {
            likeOut.noSong();
            return likeOut;
        }

        UsersPage usersPage = usersPages.get(super.getUsername());
        if(player.getAudioCollection().isSong()) {
            SongInput song = player.getAudioCollection().getSong();
            if(song == null) {
                likeOut.noSong();
                return likeOut;
            }
            return getLikeOut(usersPages, likeOut, usersPage,song);
        } else if(player.getAudioCollection().isPlaylist()){
            SongInput song = player.getAudioCollection().getPlaylist().getSongs().get(player.getAudioCollection().getPlaylist().getIndexCurrentSong());
            if(song == null) {
                likeOut.noSong();
                return likeOut;
            }
            return getLikeOut(usersPages, likeOut, usersPage,song);
        }
        return likeOut;
    }

    private LikeOut getLikeOut(HashMap<String, UsersPage> usersPages, LikeOut likeOut, UsersPage usersPage, SongInput song) {
        if(usersPage == null) {
            usersPage = new UsersPage();
            usersPage.addLikedSong(song);
            usersPages.put(super.getUsername(), usersPage);
            likeOut.setMessage("Like registered successfully.");
            return likeOut;
        }
        if(usersPage.songWithThatName(song.getName())) {
            usersPage.removeLikedSong(song.getName());
            likeOut.setMessage("Unlike registered successfully.");
        } else {
            usersPage.addLikedSong(song);
            likeOut.setMessage("Like registered successfully.");
        }
        return likeOut;
    }
}
