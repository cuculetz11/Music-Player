package detaliiPerUser;

import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class PlaylistDetailsResult {
    private String name;
    private ArrayList<String> songs;
    private String visibility;
    private int followers;

    public PlaylistDetailsResult(PlayList playlist) {
        this.name = playlist.getTitle();
        this.songs = new ArrayList<>();
        for(SongInput song : playlist.getSongs()) {
            this.songs.add(song.getName());
        }
        if(playlist.isPublicVisibility())
            this.visibility = "public";
        else
            this.visibility = "private";
        this.followers = playlist.getFollowers().size();
    }

}
