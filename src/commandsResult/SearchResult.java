package commandsResult;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class SearchResult extends CommandOut {
    private ArrayList<String> results;

    public SearchResult() {
        results = new ArrayList<>();
    }

    public void add(String song) {
        results.add(song);
    }

}
