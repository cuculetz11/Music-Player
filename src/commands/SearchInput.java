package commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commandsResult.SearchResult;
import detaliiPerUser.PlayList;
import detaliiPerUser.UsersPage;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;
import java.util.Collections;
import java.util.ArrayList;

@Setter
@Getter
public class SearchInput extends  CommandInput{
    public SearchInput() {
    }
    private String type;
    private filters filter;

    public void handleInput(JsonNode node){
        super.setUsername(node.get("username").asText());
        super.setTimestamp(node.get("timestamp").asInt());
        this.type = node.get("type").asText();
        JsonNode filtersNode = node.get("filters");
        ObjectMapper mapper = new ObjectMapper();
        this.filter = mapper.convertValue(filtersNode,filters.class);
    }

    public SearchResult execute(LibraryInput input, UsersPage userPage){
        SearchResult searchResult = new SearchResult();
        searchResult.setCommand(this.getCommand());
        searchResult.setTimestamp(this.getTimestamp());
        searchResult.setUser(this.getUsername());
        switch (this.type) {
            case "song": {
                ArrayList<SongInput> songs = input.getSongs();
                ArrayList<SongInput> songFound = new ArrayList<>();
                for (SongInput song : songs) {
                    SongInput songCopy = new SongInput();
                    songCopy.setName(song.getName());
                    songCopy.setArtist(song.getArtist());
                    songCopy.setAlbum(song.getAlbum());
                    songCopy.setGenre(song.getGenre());
                    songCopy.setLyrics(song.getLyrics());
                    songCopy.setReleaseYear(song.getReleaseYear());
                    songCopy.setTags(song.getTags());
                    songCopy.setDuration(song.getDuration());
                    songFound.add(songCopy);
                }
                if (this.filter.getName() != null) {
                    songFound = this.filter.searchAfterName(songFound);
                }
                if (this.filter.getArtist() != null) {
                    songFound = this.filter.searchAfterArtist(songFound);
                }
                if (this.filter.getAlbum() != null) {
                    songFound = this.filter.searchAfterAlbum(songFound);
                }
                if (this.filter.getGenre() != null) {
                    songFound = this.filter.searchAfterGenre(songFound);
                }
                if (this.filter.getLyrics() != null) {
                    songFound = this.filter.searchAfterLyrics(songFound);
                }
                if (this.filter.getReleaseYear() != null) {
                    songFound = this.filter.searchAfterReleaseYear(songFound);
                }
                if (this.filter.getTags() != null) {
                    songFound = this.filter.searchAfterTags(songFound);
                }
                if (songFound.size() > 5) {
                    songFound = new ArrayList<>(songFound.subList(0, 5));
                }
                if (!songFound.isEmpty())
                    Searched.addSearchedSong(songFound);

                String message = "Search returned " + songFound.size() + " results";
                searchResult.setMessage(message);
                for (SongInput song : songFound) {
                    searchResult.add(song.getName());
                }

                break;
            }
            case "podcast": {
                ArrayList<PodcastInput> podcasts = input.getPodcasts();
                ArrayList<PodcastInput> podcastFound = new ArrayList<>();
                for (PodcastInput podcast : podcasts) {
                    PodcastInput podcastCopy = new PodcastInput();
                    podcastCopy.setName(podcast.getName());
                    podcastCopy.setOwner(podcast.getOwner());
                    podcastCopy.setEpisodes(podcast.getEpisodes());
                    podcastFound.add(podcastCopy);
                }
                if (this.filter.getName() != null) {
                    podcastFound = this.filter.searchAfterNamePodcast(podcastFound);
                }
                if (this.filter.getOwner() != null) {
                    podcastFound = this.filter.searchAfterOwnerPodcast(podcastFound);
                }
                if (podcastFound.size() > 5) {
                    podcastFound = new ArrayList<>(podcastFound.subList(0, 5));
                }
                if (!podcastFound.isEmpty())
                    Searched.addSearchedPodcast(podcastFound);

                String message = "Search returned " + podcastFound.size() + " results";
                searchResult.setMessage(message);
                for (PodcastInput podcast : podcastFound) {
                    searchResult.add(podcast.getName());
                }
                break;
            }
            case "playlist": {
                ArrayList<PlayList> playLists = PublicPlaylists.getPublicPlaylists();
                ArrayList<PlayList> playListFound = new ArrayList<>();
                for (PlayList playlist : playLists) {
                    PlayList playListCopy = new PlayList();
                    playListCopy.setTitle(playlist.getTitle());
                    playListCopy.setOwner(playlist.getOwner());
                    playListCopy.setSongs(playlist.getSongs());
                    playListCopy.setCreationTime(playlist.getCreationTime());
                    playListCopy.setFollowers(playlist.getFollowers());
                    playListFound.add(playListCopy);
                }
                if (userPage != null) {
                    for (PlayList playList : userPage.getPlaylists()) {
                        if(!playList.isPublicVisibility()) {
                            PlayList playListCopy = new PlayList();
                            playListCopy.setTitle(playList.getTitle());
                            playListCopy.setOwner(playList.getOwner());
                            playListCopy.setSongs(playList.getSongs());
                            playListCopy.setCreationTime(playList.getCreationTime());
                            playListCopy.setFollowers(playList.getFollowers());
                            playListFound.add(playListCopy);
                        }
                    }
                }

                if (this.filter.getName() != null) {
                    playListFound = this.filter.searchAfterNamePlaylist(playListFound);
                }
                if (this.filter.getOwner() != null) {
                    playListFound = this.filter.searchAfterOwnerPlaylist(playListFound);
                }

                if (playListFound.size() > 5) {
                    playListFound = new ArrayList<>(playListFound.subList(0, 5));
                }

                if (!playListFound.isEmpty()) {
                    playListFound.sort((p1,p2) -> Integer.compare(p1.getCreationTime(), p2.getCreationTime()));
                    Searched.addSearchedPlaylist(playListFound);
                }

                String message = "Search returned " + Searched.getSearchedPlaylists().size() + " results";
                searchResult.setMessage(message);
                for (PlayList playList : playListFound) {
                    searchResult.add(playList.getTitle());
                }
                break;
            }
        }
        return searchResult;
    }

}
