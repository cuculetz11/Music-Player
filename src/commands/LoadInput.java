package commands;

import com.fasterxml.jackson.databind.JsonNode;
import commandPlayer.AudioCollection;
import commandPlayer.MusicPlayer;
import commandsResult.LoadOut;
import detaliiPerUser.PlayList;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public class LoadInput extends CommandInput {
    public LoadInput() {}

    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());

    }

    public LoadOut execute() {
        LoadOut loadOut = new LoadOut();
        loadOut.setTimestamp(this.getTimestamp());
        loadOut.setUser(this.getUsername());
        loadOut.setCommand(this.getCommand());
        if(!Selected.isIsSelected()) {
            loadOut.noSelection();
            return loadOut;
        }
        loadOut.succes();
        return loadOut;
            
        }
        public void loadMusicPlayer(MusicPlayer player) {
            if(Selected.isIsSelected()) {
                player.resetAll();
                player.setLoaded(true);
                player.setLastTimestamp(this.getTimestamp());
                player.setTimePlayed(0);
                player.setPaused(false);
                player.setShuffle(false);
                if(Selected.isTherePodSelected()) {
                    PodcastInput podcastSelected = Selected.getSelectedPodcast();
                    for(PodcastInput pod: MusicPlayer.getRemainedPodcasts()) {
                        if(pod.getName().equals(podcastSelected.getName())) {
                            podcastSelected = pod;
                            break;
                        }
                    }
                    AudioCollection collection = new AudioCollection();
                    collection.setPodcast(podcastSelected);
                    player.setCurrentEpisode(0);
                    player.setAudioCollection(collection);
                    player.setRemainedTime(podcastSelected.getEpisodes().get(0).getDuration());
                    player.setAudioTime(podcastSelected.getEpisodes().get(0).getDuration());

                } else if(Selected.isThereSongSelected()) {
                    AudioCollection collection = new AudioCollection();
                    SongInput song = Selected.getSelectedSong();
                    collection.setSong(song);
                    player.setAudioCollection(collection);
                    player.setRemainedTime(song.getDuration());
                    player.setAudioTime(song.getDuration());
                } else if(Selected.isTherePlaylistSelected()) {
                    AudioCollection collection = new AudioCollection();
                    PlayList playlist = Selected.getSelectedPlaylist();
                    collection.setPlaylist(playlist);
                    player.setAudioCollection(collection);
                    player.setRemainedTime(playlist.getSongs().get(0).getDuration());
                    player.setAudioTime(playlist.getSongs().get(0).getDuration());

                }
            }
                

        }

    }

