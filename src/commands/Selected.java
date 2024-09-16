package commands;

import detaliiPerUser.PlayList;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

public class Selected {
    private static SongInput selectedSong;
    private static PodcastInput selectedPodcast;
    private static PlayList selectedPlaylist;
    private static boolean isSelected;

    public static boolean isIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(boolean isSelected) {

        Selected.isSelected = isSelected;
    }

    public static boolean isThereSongSelected() {
        return selectedSong != null;
    }

    public static boolean isTherePodSelected() {
        return selectedPodcast != null;
    }

    public static boolean isTherePlaylistSelected() {
        return selectedPlaylist != null;
    }

    public static void addSelectedSong(SongInput song) {
        selectedSong = song;
        isSelected = true;
        selectedPodcast = null;
        selectedPlaylist = null;
    }
    public static void addSelectedPodcast(PodcastInput pod) {
        selectedPodcast = pod;
        isSelected = true;
        selectedSong = null;
        selectedPlaylist = null;
    }
    public static void addSelectedPlaylist(PlayList playlist) {
        selectedPlaylist = playlist;
        isSelected = true;
        selectedSong = null;
        selectedPodcast = null;
    }
    public static SongInput getSelectedSong() {
        return selectedSong;
    }
    public static PodcastInput getSelectedPodcast() {
        return selectedPodcast;
    }
    public static PlayList getSelectedPlaylist() {
        return selectedPlaylist;
    }
    public static void reset() {
        selectedSong = null;
        selectedPodcast = null;
        selectedPlaylist = null;
        isSelected = false;
    }

}
