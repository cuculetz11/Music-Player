package commandPlayer;

import detaliiPerUser.PlayList;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AudioCollection {
    private SongInput song;
    private PodcastInput podcast;
    private PlayList playlist;

    public boolean isSong(){
        return song != null;
    }
    public boolean isPodcast(){
        return podcast != null;
    }
    public boolean isPlaylist(){return playlist != null;}
}
