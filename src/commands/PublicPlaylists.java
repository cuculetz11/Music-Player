package commands;

import detaliiPerUser.PlayList;
import lombok.Getter;

import java.util.ArrayList;

public class PublicPlaylists {
    private static ArrayList<PlayList> publicPlaylists = new ArrayList<>();

    public static ArrayList<PlayList> getPublicPlaylists() {
        return publicPlaylists;
    }


    public static void addPublicPlaylist(PlayList playlist) {
        publicPlaylists.add(playlist);
    }

    public static void removePublicPlaylist(PlayList playlist) {
        publicPlaylists.remove(playlist);
    }
    public static void reset() {
        publicPlaylists = new ArrayList<>();
    }
}