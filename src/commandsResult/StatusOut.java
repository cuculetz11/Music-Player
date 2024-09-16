package commandsResult;

import commandPlayer.MusicPlayer;
import commands.Stats;
import detaliiPerUser.PlayList;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusOut {
    private String command;
    private String user;
    private int timestamp;
    private Stats stats;

    public void StatsSong(MusicPlayer player, SongInput song) {
        if (song != null) {
            String repeat = "";
            if(player.getRepeat() == 0) {
                repeat = "No Repeat";
            } else if(player.getRepeat() == 1) {
                repeat = "Repeat Once";
            } else if(player.getRepeat() == 2) {
                repeat = "Repeat Infinite";
            }
            this.stats = new Stats(song.getName(), player.getRemainedTime(), repeat, player.isShuffle(), player.isPaused());
        }
    }
    public void StatsPodcast(MusicPlayer player, PodcastInput pod) {
        if (pod != null) {
            EpisodeInput episodePlaying = pod.getEpisodes().get(player.getCurrentEpisode());
            String repeat = "";
            if(player.getRepeat() == 0) {
                repeat = "No Repeat";
            } else if(player.getRepeat() == 1) {
                repeat = "Repeat Once";
            } else if(player.getRepeat() == 2) {
                repeat = "Repeat Infinite";
            }
            this.stats = new Stats(episodePlaying.getName(), player.getRemainedTime(), repeat, player.isShuffle(), player.isPaused());
        }
    }
    public void StatsPlaylist(MusicPlayer player, PlayList playlist) {
        if (playlist != null && !playlist.getSongs().isEmpty()) { // Check if songs are not empty
            SongInput song = playlist.getSongs().get(playlist.getIndexCurrentSong());
            String repeat = "";
            if(player.getRepeat() == 0) {
                repeat = "No Repeat";
            } else if(player.getRepeat() == 1) {
                repeat = "Repeat All";
            } else if(player.getRepeat() == 2) {
                repeat = "Repeat Current Song";
            }
            this.stats = new Stats(song.getName(), player.getRemainedTime(),
                    repeat, player.isShuffle(), player.isPaused());
        }
    }
    public StatusOut(String command, String user, int timestamp, MusicPlayer player) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        if (player.isLoaded()) {
            if(player.getAudioCollection().isSong()) {
                StatsSong(player, player.getAudioCollection().getSong());
            } else if(player.getAudioCollection().isPodcast()) {
               StatsPodcast(player, player.getAudioCollection().getPodcast());
            } else if(player.getAudioCollection().isPlaylist()) {
                StatsPlaylist(player, player.getAudioCollection().getPlaylist());
            }
        } else {
            this.stats = new Stats();
        }

    }

}
