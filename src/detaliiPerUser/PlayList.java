package detaliiPerUser;

import commands.PublicPlaylists;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;

@Setter
@Getter
public class PlayList {
    private String title;
    private String owner;
    private ArrayList<String> followers;
    private int indexCurrentSong;
    private int creationTime;
    private ArrayList<SongInput> songs;
    private boolean publicVisibility;

    public PlayList(String title, String owner,int timestamp) {
        this.title = title;
        this.owner = owner;
        this.songs = new ArrayList<>();
        this.indexCurrentSong = 0;
        this.creationTime = timestamp;
        this.followers = new ArrayList<>();
        this.publicVisibility = true;
        PublicPlaylists.addPublicPlaylist(this);
    }
    public PlayList() {
        this.songs = new ArrayList<>();
        this.publicVisibility = true;

    }

    public void addFollower(String username) {
        this.followers.add(username);
    }
    public void removeFollower(String username) {
        this.followers.remove(username);
    }
    public boolean isAFollower(String username) {
        for (String follower : followers) {
            if (follower.equals(username)) {
                return true;
            }
        }
        return false;
    }
    public boolean SongAfterName(String songName) {
        for (SongInput song : songs) {
            if (song.getName().equals(songName)) {
                return true;
            }
        }
        return false;

    }

    public void removeSong(String songName) {
        Iterator<SongInput> iterator = songs.iterator();
        while (iterator.hasNext()) {
            SongInput song = iterator.next();
            if (song.getName().equals(songName)) {
                iterator.remove();
                break;
            }
        }
    }
    public void addSong(SongInput song) {
        this.songs.add(song);
    }
}
