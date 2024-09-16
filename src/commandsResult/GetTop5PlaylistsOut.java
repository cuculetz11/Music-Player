package commandsResult;

import commands.PublicPlaylists;
import detaliiPerUser.PlayList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class GetTop5PlaylistsOut {
    private String command;
    private int timestamp;
    private ArrayList<String> result;

    public GetTop5PlaylistsOut(String command, int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
        this.result = new ArrayList<>();
    }
    public void execute() {
        ArrayList<PlayList> playlists = PublicPlaylists.getPublicPlaylists();
        playlists.sort((p1,p2) -> Integer.compare(p1.getCreationTime(), p2.getCreationTime()));
        playlists.sort((p1,p2) -> Integer.compare(p2.getFollowers().size(), p1.getFollowers().size()));
        int n = playlists.size();
        if (n > 5)
            n = 5;

        for (int i = 0; i < n; i++) {
            this.result.add(playlists.get(i).getTitle());
        }

    }
}
