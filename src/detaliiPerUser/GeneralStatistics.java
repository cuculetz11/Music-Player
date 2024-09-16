package detaliiPerUser;

import fileio.input.LibraryInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class GeneralStatistics {
    private static ArrayList<SongLikeInfo> likedSongs = new ArrayList<>();
    private static LibraryInput library;
    public static void reset() {
        likedSongs.clear();
    }

    public static ArrayList<SongLikeInfo> getLikedSongs() {
        return likedSongs;
    }

    public static void setLikedSongs(ArrayList<SongLikeInfo> likedSongs) {
        GeneralStatistics.likedSongs = likedSongs;
    }

    public static LibraryInput getLibrary() {
        return library;
    }

    public static void setLibrary(LibraryInput library) {
        GeneralStatistics.library = library;
    }
}
