package commandsResult;

import com.fasterxml.jackson.databind.JsonNode;
import detaliiPerUser.PlayList;
import detaliiPerUser.PlaylistDetailsResult;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class showPlaylistOut {
    private String command;
    private String user;
    private int timestamp;
    private ArrayList<PlaylistDetailsResult> result;

    public showPlaylistOut(String command, JsonNode node) {
        this.command = command;
        this.user = node.get("username").asText();
        this.timestamp = node.get("timestamp").asInt();
        this.result = new ArrayList<>();
    }
    public void addToResult (ArrayList<PlayList> playLists) {
        for (PlayList playList : playLists) {
            PlaylistDetailsResult result = new PlaylistDetailsResult(playList);
            this.result.add(result);
        }
    }

}
