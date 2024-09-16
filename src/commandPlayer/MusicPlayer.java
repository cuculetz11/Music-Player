package commandPlayer;

import detaliiPerUser.PlayList;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MusicPlayer {
    private boolean paused;
    private boolean shuffle;
    private int shuffleSeed;
    private boolean isLoaded;
    private AudioCollection audioCollection;
    private int currentEpisode;
    //remainedTime + timePlayed = AudioTime
    private int remainedTime;
    private int lastTimestamp;
    private int timePlayed;
    private int audioTime;
    private int repeat;
    private static ArrayList<PodcastInput> remainedPodcasts = new ArrayList<>();

    public int getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(int currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public static void addRemainedPodcast(PodcastInput podcast) {
        remainedPodcasts.add(podcast);
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public MusicPlayer() {
        audioCollection = new AudioCollection();
        remainedTime = 0;
        shuffleSeed = 0;
        lastTimestamp = 0;
        timePlayed = 0;
        currentEpisode = 0;
        audioTime = 0;
        paused = true;
        shuffle = false;
        isLoaded = false;
        repeat = 0;
    }
    public void VerifyTime(int timestamp){

        if (!this.isPaused() && this.isLoaded()) {
            if (this.getAudioCollection().isSong()) {
                verifySongTime(timestamp);
            } else if (this.getAudioCollection().isPodcast()) {
                verifyPodcastTime(timestamp);
            } else if (this.getAudioCollection().isPlaylist()) {
                verifyPlaylistTime(timestamp);
            }
        }


    }
    public void updateTime(int timestamp){
        this.timePlayed = timestamp - this.lastTimestamp + this.timePlayed;
        this.remainedTime = this.audioTime - this.timePlayed;
        this.lastTimestamp = timestamp;
    }

    public void verifySongTime(int timestamp){
        updateTime(timestamp);
        if(remainedTime <= 0 && repeat != 2){
            if(repeat == 0) {
                this.resetAll();
            } else if(repeat == 1){
                    repeat = 0;
                    this.timePlayed = Math.abs(this.remainedTime);
                    this.remainedTime = this.audioTime - this.timePlayed;
                    if(this.remainedTime <= 0){
                        this.resetAll();
                    }
                }
            } else if(repeat == 2){
                while(this.remainedTime <= 0){
                    this.timePlayed = Math.abs(this.remainedTime);
                    this.remainedTime = this.audioTime - this.timePlayed;
                }

            }
        }


    public void processRemainingEpisodes(ArrayList<EpisodeInput> episodes) {
        currentEpisode++;
        while (currentEpisode < episodes.size()) {
            this.audioTime = episodes.get(currentEpisode).getDuration();
            this.remainedTime = this.audioTime + this.remainedTime;
            if (remainedTime > 0) {
                this.timePlayed = this.audioTime - this.remainedTime;
                break;
            }
            currentEpisode++;
        }
    }

//    Exact, în cazul în care shuffle este activat, ai două etape esențiale:
//
//    Găsești poziția curentă în lista de indici amestecați (adică poziția melodiei curente în ordinea amestecată).
//    Selectezi următorul indice din lista amestecată, care se află la poziția curentă + 1.
//

    public ArrayList<Integer> generateShuffleArray(int seed, int size) {
        Random random = new Random(this.shuffleSeed);
        ArrayList<Integer> indices = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
        return indices;
    }
    public void processRemainingSongs(PlayList playList) {
        ArrayList<SongInput> songs = playList.getSongs();
        int n = songs.size();

        if (!this.shuffle) {
            if (playList.getIndexCurrentSong() < n - 1) {
                playList.setIndexCurrentSong(playList.getIndexCurrentSong() + 1);
            } else {
                // daca am ajuns la ultima melodie oprim functia
                return;
            }
        } else {
            //vectorul de indici amestecat
            ArrayList<Integer> shuffledIndices = generateShuffleArray(this.shuffleSeed, n);
            int currentIndexInShuffled = shuffledIndices.indexOf(playList.getIndexCurrentSong());
            //cautam poztia melodiei curente in vectorul amestecat de indici

            if (currentIndexInShuffled < n - 1) {
                //vreau sa scot numarul(melodia) ce se afla la urmatorul index
                playList.setIndexCurrentSong(shuffledIndices.get(currentIndexInShuffled + 1));
            } else {
                return;
            }
        }

        // Procesăm melodiile rămase
        while (true) {
            this.audioTime = songs.get(playList.getIndexCurrentSong()).getDuration();
            this.remainedTime = this.audioTime + this.remainedTime;

            if (remainedTime > 0) {
                this.timePlayed = this.audioTime - this.remainedTime;
                break;
            }

            if (!this.shuffle) {
                if (playList.getIndexCurrentSong() < n - 1) {
                    playList.setIndexCurrentSong(playList.getIndexCurrentSong() + 1);
                } else {
                    //oprim for ul cand ajungem la capatul playlistului
                    break;
                }
            } else {
                ArrayList<Integer> shuffledIndices = generateShuffleArray(this.shuffleSeed, n);
                int currentIndexInShuffled = shuffledIndices.indexOf(playList.getIndexCurrentSong());

                if (currentIndexInShuffled < n - 1) {
                    playList.setIndexCurrentSong(shuffledIndices.get(currentIndexInShuffled + 1));
                } else {
                    break;
                }
            }
        }
    }


    public void verifyPodcastTime(int timestamp){
        if(!this.paused) {
            updateTime(timestamp);
            if(this.remainedTime < 0){
                ArrayList<EpisodeInput> episodes = audioCollection.getPodcast().getEpisodes();
                processRemainingEpisodes(episodes);
                if(this.remainedTime <= 0){
                    this.resetAll();
                }
            }
        }
    }

//    public void repeatPlaylist(){
//        while(this.remainedTime <= 0){
//
//        }
//
//    }
    public void verifyPlaylistTime(int timestamp){
       updateTime(timestamp);
        if(this.remainedTime < 0 && repeat != 2){
            processRemainingSongs(this.audioCollection.getPlaylist());
            if(this.remainedTime <= 0){

                if(repeat == 0) {
                    this.resetAll();
                } else if(repeat == 1){
                    while(this.remainedTime <= 0) {
                        int startIndex = 0;
                        if(this.shuffle) {
                           ArrayList<Integer> indices = generateShuffleArray(this.shuffleSeed, this.audioCollection.getPlaylist().getSongs().size());
                           startIndex = indices.get(0);
                        }
                        this.audioCollection.getPlaylist().setIndexCurrentSong(startIndex);
                        this.timePlayed = Math.abs(this.remainedTime);
                        this.audioTime = this.audioCollection.getPlaylist().getSongs().get(startIndex).getDuration();
                        this.remainedTime = this.audioTime - this.timePlayed;
                        if (this.remainedTime <= 0) {
                            processRemainingSongs(this.audioCollection.getPlaylist());
                        }
                    }
                }
            }

        }
        if(repeat == 2) {
            while(this.remainedTime <= 0){
                this.timePlayed = Math.abs(this.remainedTime);
                this.remainedTime = this.audioTime - this.timePlayed;
            }
        }
    }
    public void resetAll() {
        remainedTime = 0;
        lastTimestamp = 0;
        timePlayed = 0;
        audioTime = 0;
        currentEpisode = 0;
        paused = true;
        shuffle = false;
        isLoaded = false;
        audioCollection = new AudioCollection();
        repeat = 0;
    }
    public void wasPodcast(int commandTimestamp){
        if(this.getAudioCollection().isPodcast()) {
            this.verifyPodcastTime(commandTimestamp);
            if(this.getAudioCollection().getPodcast() != null) {
                if (currentEpisode < this.getAudioCollection().getPodcast().getEpisodes().size() - 1) {
                    PodcastInput podcast = new PodcastInput();
                    podcast.setName(this.getAudioCollection().getPodcast().getName());
                    podcast.setOwner(this.getAudioCollection().getPodcast().getOwner());
                    ArrayList<EpisodeInput> episodes = new ArrayList<>(audioCollection.getPodcast().getEpisodes().subList(currentEpisode, audioCollection.getPodcast().getEpisodes().size()));
                    episodes.get(0).setDuration(this.remainedTime);
                    podcast.setEpisodes(episodes);
                    remainedPodcasts.add(podcast);


                }
            }
        }
    }

    public static ArrayList<PodcastInput> getRemainedPodcasts() {
        return remainedPodcasts;
    }

    public static void setRemainedPodcasts(ArrayList<PodcastInput> remainedPodcasts) {
        MusicPlayer.remainedPodcasts = remainedPodcasts;
    }

    public int getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(int audioTime) {
        this.audioTime = audioTime;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(int lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getRemainedTime() {
        return remainedTime;
    }

    public void setRemainedTime(int remainedTime) {
        this.remainedTime = remainedTime;
    }

    public AudioCollection getAudioCollection() {
        return audioCollection;
    }

    public void setAudioCollection(AudioCollection audioCollection) {
        this.audioCollection = audioCollection;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
