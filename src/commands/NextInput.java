package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.MusicPlayer;
import detaliiPerUser.PlayList;

import java.util.ArrayList;

public class NextInput extends CommandInput{
    public NextInput(JsonNode node, String command) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        super.setCommand(command);
    }

    public String execute(MusicPlayer player) {
        String message = "";
        if(!player.isLoaded()) {
            message = "Please load a source before skipping to the next track.";
            return message;
        } else {
            if (player.getAudioCollection().isSong()) {
                if (player.getRepeat() == 0) {
                    player.resetAll();
                    message = "Please load a source before skipping to the next track.";
                    return message;
                }
                if (player.getRepeat() == 1) {
                    player.setRepeat(0);
                    player.setTimePlayed(0);
                    player.setPaused(false);
                    player.setLastTimestamp(this.getTimestamp());
                    player.setRemainedTime(player.getAudioTime());
                    message = "Skipped to next track successfully. The current track is " + player.getAudioCollection().getSong().getName() + ".";
                    return message;
                }
                if (player.getRepeat() == 2) {
                    player.setTimePlayed(0);
                    player.setPaused(false);
                    player.setLastTimestamp(this.getTimestamp());
                    player.setRemainedTime(player.getAudioTime());
                    message = "Skipped to next track successfully. The current track is " + player.getAudioCollection().getSong().getName() + ".";
                    return message;
                }

            } else if (player.getAudioCollection().isPlaylist()) {
                if (player.getRepeat() != 2) {
                    PlayList playlist = player.getAudioCollection().getPlaylist();
                    if (!player.isShuffle()) {
                        playlist.setIndexCurrentSong(playlist.getIndexCurrentSong() + 1);
                    } else {
                        ArrayList<Integer> shuffledIndices = player.generateShuffleArray(player.getShuffleSeed(), playlist.getSongs().size());
                        int currentIndexInShuffled = shuffledIndices.indexOf(playlist.getIndexCurrentSong());
                        if(currentIndexInShuffled + 1 >= shuffledIndices.size()) {
                            playlist.setIndexCurrentSong(currentIndexInShuffled + 1);
                        } else {
                            playlist.setIndexCurrentSong(shuffledIndices.get(currentIndexInShuffled + 1));
                        }

                    }
                    if (player.getRepeat() == 0) {
                        if (playlist.getIndexCurrentSong() >= playlist.getSongs().size()) {
                            player.resetAll();
                            message = "Please load a source before skipping to the next track.";
                            return message;
                        } else {
                            player.setAudioTime(playlist.getSongs().get(playlist.getIndexCurrentSong()).getDuration());
                            player.setTimePlayed(0);
                            player.setPaused(false);
                            player.setLastTimestamp(this.getTimestamp());
                            player.setRemainedTime(player.getAudioTime());
                            message = "Skipped to next track successfully. The current track is " + playlist.getSongs().get(playlist.getIndexCurrentSong()).getName() + ".";
                            return message;
                        }
                    } else if (player.getRepeat() == 1) {
                        if (playlist.getIndexCurrentSong() >= playlist.getSongs().size()) {
                            if (player.isShuffle()) {
                                ArrayList<Integer> shuffledIndices = player.generateShuffleArray(player.getShuffleSeed(), playlist.getSongs().size());
                                int currentIndexInShuffled = 0;
                                //aici nu sunt sigur
                                playlist.setIndexCurrentSong(shuffledIndices.get(currentIndexInShuffled));
                            } else {
                                playlist.setIndexCurrentSong(0);
                            }
                        }
                            player.setAudioTime(playlist.getSongs().get(playlist.getIndexCurrentSong()).getDuration());
                            player.setTimePlayed(0);
                            player.setPaused(false);
                            player.setLastTimestamp(this.getTimestamp());
                            player.setRemainedTime(player.getAudioTime());
                            message = "Skipped to next track successfully. The current track is " + playlist.getSongs().get(playlist.getIndexCurrentSong()).getName() + ".";
                            return message;

                    }
                } else {
                    player.setTimePlayed(0);
                    player.setRemainedTime(player.getAudioTime());
                    message = "Skipped to next track successfully. The current track is " + player.getAudioCollection().getPlaylist().getSongs().get(player.getAudioCollection().getPlaylist().getIndexCurrentSong()).getName() + ".";
                    return message;
                }

            } else if (player.getAudioCollection().isPodcast()) {
                if(player.getRepeat() == 0) {
                    if(player.getCurrentEpisode() + 1 == player.getAudioCollection().getPodcast().getEpisodes().size()) {
                        player.resetAll();
                        message = "Please load a source before skipping to the next track.";
                        return message;
                    } else {
                        player.setCurrentEpisode(player.getCurrentEpisode() + 1);
                        player.setTimePlayed(0);
                        player.setPaused(false);
                        player.setLastTimestamp(this.getTimestamp());
                        player.setAudioTime(player.getAudioCollection().getPodcast().getEpisodes().get(player.getCurrentEpisode()).getDuration());
                        player.setRemainedTime(player.getAudioCollection().getPodcast().getEpisodes().get(player.getCurrentEpisode()).getDuration());
                        message = "Skipped to next track successfully. The current track is " + player.getAudioCollection().getPodcast().getEpisodes().get(player.getCurrentEpisode()).getName() + ".";
                        return message;
                    }
                }
            }

        }
        return message;
    }
}
