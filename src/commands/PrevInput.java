package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import detaliiPerUser.PlayList;

import java.util.ArrayList;

public class PrevInput extends CommandInput{
    public PrevInput(JsonNode node, String command) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        super.setCommand(command);
    }
    public String execute(MusicPlayer player) {
        String message = "";
        if(!player.isLoaded()) {
            message = "Please load a source before returning to the previous track.";
            return message;
        } else {
            if (player.getAudioCollection().isSong()) {
                player.setTimePlayed(0);
                player.setPaused(false);
                player.setLastTimestamp(this.getTimestamp());
                player.setRemainedTime(player.getAudioTime());
                message = "Returned to previous track successfully. The current track is " + player.getAudioCollection().getSong().getName() + ".";
                return message;
            }
            if(player.getAudioCollection().isPlaylist()) {
                if(!player.isShuffle()) {
                    if(player.getAudioCollection().getPlaylist().getIndexCurrentSong() == 0 || player.getTimePlayed() >= 1) {
                        player.setTimePlayed(0);
                        player.setPaused(false);
                        player.setLastTimestamp(this.getTimestamp());
                        player.setRemainedTime(player.getAudioTime());
                        message = "Returned to previous track successfully. The current track is " + player.getAudioCollection().getPlaylist().getSongs().get(player.getAudioCollection().getPlaylist().getIndexCurrentSong()).getName() + ".";
                        return message;
                    } else {
                        player.getAudioCollection().getPlaylist().setIndexCurrentSong(player.getAudioCollection().getPlaylist().getIndexCurrentSong() - 1);
                        player.setTimePlayed(0);
                        player.setPaused(false);
                        player.setLastTimestamp(this.getTimestamp());
                        player.setAudioTime(player.getAudioCollection().getPlaylist().getSongs().get(player.getAudioCollection().getPlaylist().getIndexCurrentSong()).getDuration());
                        player.setRemainedTime(player.getAudioTime());
                        message = "Returned to previous track successfully. The current track is " + player.getAudioCollection().getPlaylist().getSongs().get(player.getAudioCollection().getPlaylist().getIndexCurrentSong()).getName() + ".";
                        return message;
                        }
                    } else {
                    PlayList playlist = player.getAudioCollection().getPlaylist();
                    ArrayList<Integer> shuffledIndices = player.generateShuffleArray(player.getShuffleSeed(), playlist.getSongs().size());
                    int currentIndexInShuffled = shuffledIndices.indexOf(playlist.getIndexCurrentSong());
                    if(currentIndexInShuffled == 0 || player.getTimePlayed() >= 1) {
                        player.setTimePlayed(0);
                        player.setPaused(false);
                        player.setLastTimestamp(this.getTimestamp());
                        player.setRemainedTime(player.getAudioTime());
                        message = "Returned to previous track successfully. The current track is " + player.getAudioCollection().getPlaylist().getSongs().get(playlist.getIndexCurrentSong()).getName() + ".";
                        return message;
                    } else {
                        currentIndexInShuffled = currentIndexInShuffled - 1;
                        playlist.setIndexCurrentSong(shuffledIndices.get(currentIndexInShuffled));
                        player.setTimePlayed(0);
                        player.setPaused(false);
                        player.setLastTimestamp(this.getTimestamp());
                        player.setAudioTime(playlist.getSongs().get(playlist.getIndexCurrentSong()).getDuration());
                        player.setRemainedTime(player.getAudioTime());
                        message = "Returned to previous track successfully. The current track is " + player.getAudioCollection().getPlaylist().getSongs().get(playlist.getIndexCurrentSong()).getName() + ".";
                        return message;
                    }
                }
            }
            if(player.getAudioCollection().isPodcast()) {
                message = "is podcast prev look at ref";
            }
        }
        return message;
    }
}
