package commandsResult;

public class LoadOut extends CommandOut{
    public LoadOut() {}

    public void succes() {
        this.setMessage("Playback loaded successfully.");
    }
    public void noSelection() {
        this.setMessage("Please select a source before attempting to load.");
    }
    public void noSource() {
        this.setMessage("You can't load an empty audio collection!");
    }
}
