package detaliiPerUser;

import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;

@Getter
public class UsersPage {
    private ArrayList<SongInput> likedSongs;
    private ArrayList<PlayList> playlists;
    public UsersPage() {
        likedSongs = new ArrayList<>();
        playlists = new ArrayList<>();
    }

    public boolean playlistWithThatName(String playlistName) {
        for (PlayList playlist : playlists) {
            if(playlist.getTitle().equals(playlistName)) {
                return true;
            }

        }
        return false;
    }

    public boolean songWithThatName(String songName) {
        for (SongInput song : likedSongs) {
            if(song.getName().equals(songName)) {
                return true;
            }
        }
        return false;
    }

    public void removeLikedSong(String songName) {
        Iterator<SongInput> iterator = likedSongs.iterator();
        while(iterator.hasNext()) {
            SongInput song = iterator.next();
            if(song.getName().equals(songName)) {
                iterator.remove();
                break;
            }
        }
        for(int i = 0; i < GeneralStatistics.getLikedSongs().size(); i++) {
            if(GeneralStatistics.getLikedSongs().get(i).getSongName().equals(songName)) {
                GeneralStatistics.getLikedSongs().get(i).removeLike();
                break;
            }
        }

    }

    public void addLikedSong(SongInput song) {
        likedSongs.add(song);
        int exists = 0;
        for(int i = 0; i < GeneralStatistics.getLikedSongs().size(); i++) {
            if(GeneralStatistics.getLikedSongs().get(i).getSongName().equals(song.getName())) {
                GeneralStatistics.getLikedSongs().get(i).addLike();
                exists++;
                break;
            }
        }
        if(exists == 0) {
            SongLikeInfo likedSong = new SongLikeInfo(song.getName());
            likedSong.addLike();
            GeneralStatistics.getLikedSongs().add(likedSong);
        }
    }
    public void createPlaylist(PlayList playlist) {
        playlists.add(playlist);

    }
}
