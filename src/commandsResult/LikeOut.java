package commandsResult;

public class LikeOut extends CommandOut{
    public LikeOut() {}
    public void LikeSuccess() {
        this.setMessage("Like registered successfully.");
    }
    public void UnlikeSuccess() {
        this.setMessage("Unlike registered successfully.");
    }
    public void noSong() {
        this.setMessage("Loaded source is not a song.");
    }
    public void noSource() {
        this.setMessage("Please load a source before liking or unliking.");
    }
}
