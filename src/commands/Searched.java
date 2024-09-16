package commands;

import detaliiPerUser.PlayList;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public class Searched {
    private static ArrayList<SongInput> searchedSongs = new ArrayList<>();
    private static ArrayList<PodcastInput> searchedPodcasts = new ArrayList<>();
    private static ArrayList<PlayList> searchedPlaylists = new ArrayList<>();
    private static boolean isSearched;

    public static boolean isIsSearched() {
        return isSearched;
    }

    public static void setIsSearched(boolean isSearched) {
        Searched.isSearched = isSearched;
    }

    public static boolean isTherePlaylist() {
        return !searchedPlaylists.isEmpty();
    }
    public static boolean isThereSongs() {
        return !searchedSongs.isEmpty();
    }
    public static boolean isTherePodcasts() {
        return !searchedPodcasts.isEmpty();
    }
    public static void addSearchedSong(ArrayList<SongInput> Songs) {
        searchedSongs.clear();
        searchedSongs.addAll(Songs);
        isSearched = true;
    }

    public static void addSearchedPodcast(ArrayList<PodcastInput> Podcasts) {
        searchedPodcasts.clear();
        searchedPodcasts.addAll(Podcasts);
        isSearched = true;
    }

    public static void addSearchedPlaylist(ArrayList<PlayList> Playlists) {
        searchedPlaylists.clear();
        searchedPlaylists.addAll(Playlists);
        isSearched = true;


    }
    public static  ArrayList<SongInput> getSearchedSongs() {
        return searchedSongs;
    }
    public static ArrayList<PodcastInput> getSearchedPodcasts() {
        return searchedPodcasts;
    }
    public static ArrayList<PlayList> getSearchedPlaylists() { return searchedPlaylists; }

    public static void reset() {
        searchedSongs = new ArrayList<>();
        searchedPodcasts = new ArrayList<>();
        searchedPlaylists = new ArrayList<>();
        isSearched = false;
    }


}
