package detaliiPerUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongLikeInfo {
    private String songName;
    private int likes;
    private int indexFromLibrary;

    public SongLikeInfo(String songName) {
        this.songName = songName;
        this.likes = 0;
    }
    public void addLike() {
        this.likes++;
    }
    public void removeLike() {
        this.likes--;
    }

}
