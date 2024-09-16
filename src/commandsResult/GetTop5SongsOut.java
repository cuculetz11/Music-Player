package commandsResult;
import java.util.ArrayList;
import java.util.Iterator;

import detaliiPerUser.GeneralStatistics;
import detaliiPerUser.SongLikeInfo;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class GetTop5SongsOut {
    private String command;
    private int timestamp;
    private ArrayList<String> result;

    public GetTop5SongsOut(String command, int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
        this.result = new ArrayList<>();

    }

    public int indexFromLibrary(String songname) {
        ArrayList<SongInput> songs = GeneralStatistics.getLibrary().getSongs();
        for(int i = 0 ; i < songs.size() ; i++) {
            if(songs.get(i).getName().equals(songname)) {
                return i;
            }
        }
        return -1;
    }

    public void execute() {
        Iterator<SongLikeInfo> iterator = GeneralStatistics.getLikedSongs().iterator();
        while (iterator.hasNext()) {
            SongLikeInfo song = iterator.next();
            if(song.getLikes() == 0) {
                iterator.remove();
            }
        }
        int n = GeneralStatistics.getLikedSongs().size();
        if(n < 5) {
            int x = 5 - n;
            for (SongInput song : GeneralStatistics.getLibrary().getSongs()) {
                int exists = 0;
                for (int i = 0; i < GeneralStatistics.getLikedSongs().size(); i++) {
                    if(song.getName().equals(GeneralStatistics.getLikedSongs().get(i).getSongName())) {
                        exists++;
                        break;
                    }
                }
                if (exists == 0) {
                    SongLikeInfo likedSong = new SongLikeInfo(song.getName());
                    GeneralStatistics.getLikedSongs().add(likedSong);
                    x--;
                }
                if (x == 0)
                    break;
            }
        }

        ArrayList<SongLikeInfo> songs = GeneralStatistics.getLikedSongs();
        for(int i = 0; i < songs.size(); i++) {
            songs.get(i).setIndexFromLibrary(indexFromLibrary(songs.get(i).getSongName()));
        }
        songs.sort((s1,s2) -> Integer.compare(s1.getIndexFromLibrary(), s2.getIndexFromLibrary()));
        songs.sort((s1,s2) -> Integer.compare(s2.getLikes(), s1.getLikes()));
        for (int i = 0; i < 5; i++) {
            this.result.add(songs.get(i).getSongName());
        }
    }
}
