package commandsResult;

public class ShuffleOut extends CommandOut{
    public ShuffleOut() {}
    public void noSource() {
        this.setMessage("Please load a source before using the shuffle function.");
    }
    public void isNotPlaylist() {
        this.setMessage("The loaded source is not a playlist.");
    }
    public void activated() {
        this.setMessage("Shuffle function activated successfully.");
    }
    public void disabled() {
        this.setMessage("Shuffle function deactivated successfully.");
    }
}
