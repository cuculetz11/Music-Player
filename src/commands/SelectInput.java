package commands;

import com.fasterxml.jackson.databind.JsonNode;

import commandsResult.SearchResult;
import commandsResult.SelectResult;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SelectInput extends CommandInput{
    private int itemNumber;

    public SelectInput() {}

    public void handleInput(JsonNode node) {
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        this.itemNumber = node.get("itemNumber").asInt();
    }

    public void addInSelectionList(SelectResult selectResult, int size) {
        String message = "";
        if(this.getItemNumber() > size ) {
            message = "The selected ID is too high.";
            Selected.setIsSelected(false);
        } else {
            if(Searched.isTherePodcasts()) {
                Selected.addSelectedPodcast(Searched.getSearchedPodcasts().get(this.getItemNumber() - 1));
                message = "Successfully selected " + Searched.getSearchedPodcasts().get(this.getItemNumber() - 1).getName() + ".";
            }
            if(Searched.isThereSongs()) {
                Selected.addSelectedSong(Searched.getSearchedSongs().get(this.getItemNumber() - 1));
                message = "Successfully selected " + Searched.getSearchedSongs().get(this.getItemNumber() - 1).getName() + ".";
            }
            if (Searched.isTherePlaylist()) {
                Selected.addSelectedPlaylist(Searched.getSearchedPlaylists().get(this.getItemNumber() - 1));
                message = "Successfully selected " + Searched.getSearchedPlaylists().get(this.getItemNumber() - 1).getTitle() + ".";
            }
            Selected.setIsSelected(true);
        }
        selectResult.setMessage(message);
    }
    public SelectResult execute() {
        SelectResult selectResult = new SelectResult();
        selectResult.setCommand((this.getCommand()));
        selectResult.setUser(this.getUsername());
        selectResult.setTimestamp(this.getTimestamp());
        if(!Searched.isIsSearched()) {
            String message = "Please conduct a search before making a selection.";
            Selected.reset();
            selectResult.setMessage(message);
        } else {
                if(Searched.isTherePodcasts()) {
                    addInSelectionList(selectResult, Searched.getSearchedPodcasts().size());
                }else if(Searched.isThereSongs()) {
                    addInSelectionList(selectResult, Searched.getSearchedSongs().size());
                }else if(Searched.isTherePlaylist()) {
                    addInSelectionList(selectResult,Searched.getSearchedPlaylists().size());
                } else {
                    selectResult.setMessage("The selected ID is too high.");
                    Selected.setIsSelected(false);
                    // asta de aici inseamna ca a fost facut un search inainte dar a dat 0 rezultate
                }
            }
            return selectResult;
        }




}
