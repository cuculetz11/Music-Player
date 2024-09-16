package commands;
import detaliiPerUser.PlayList;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class filters {
    private String name;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;

    public filters() {}

    public ArrayList<SongInput> searchAfterName(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput song : songs) {
            if(song.getName().startsWith(name)) {
                filteredSongs.add(song);
            }

        }
        return filteredSongs;
    }
    public ArrayList<SongInput> searchAfterAlbum(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput song : songs) {
            if(song.getAlbum().equals(album)) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }
    public ArrayList<SongInput> searchAfterGenre(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput song : songs) {
            if(song.getGenre().equalsIgnoreCase(genre)) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }
    public ArrayList<SongInput> searchAfterReleaseYear(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        String substring = releaseYear.substring(1);
        int year = Integer.parseInt(substring);

        for (SongInput song : songs) {
            if (releaseYear.charAt(0) == '>') {
                if (song.getReleaseYear() > year) {
                    filteredSongs.add(song);
                }
            } else if (releaseYear.charAt(0) == '<') {
                if (song.getReleaseYear() < year) {
                    filteredSongs.add(song);
                }
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> searchAfterArtist(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput song : songs) {
            if(song.getArtist().equals(artist)) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> searchAfterLyrics(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput song : songs) {
            String lyric = song.getLyrics();
            if (lyric.contains(lyrics)) {
                filteredSongs.add(song);
            } else {
                for (String word : lyric.split(" ")) {
                    if (word.toLowerCase().startsWith(lyrics.toLowerCase())) {
                        filteredSongs.add(song);
                        break;
                    }
                    if (word.toLowerCase().contains(lyrics.toLowerCase())) {
                        filteredSongs.add(song);
                        break;
                    }
                }
            }
        }
        return filteredSongs;
    }
    public ArrayList<SongInput> searchAfterTags(ArrayList<SongInput> songs) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput song : songs) {
            int ok = 0;
            for(String tag : tags) {
                if(song.getTags().contains(tag)) {
                    ok++;
                }
            }
            if(ok == tags.size()) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }

    public ArrayList<PodcastInput> searchAfterNamePodcast(ArrayList<PodcastInput> podcasts) {
        ArrayList<PodcastInput> filteredPodcasts = new ArrayList<>();
        for (PodcastInput podcast : podcasts) {
            if(podcast.getName().startsWith(name)) {
                filteredPodcasts.add(podcast);
            }

        }
        return filteredPodcasts;
    }
    public ArrayList<PodcastInput> searchAfterOwnerPodcast(ArrayList<PodcastInput> podcasts) {
        ArrayList<PodcastInput> filteredPodcasts = new ArrayList<>();
        for (PodcastInput podcast : podcasts) {
            if(podcast.getOwner().equals(owner)) {
                filteredPodcasts.add(podcast);
            }
        }
        return filteredPodcasts;
    }

    public ArrayList<PlayList> searchAfterNamePlaylist(ArrayList<PlayList> playlists) {
        ArrayList<PlayList> filteredPlaylists = new ArrayList<>();
        for (PlayList playlist : playlists) {
            if(playlist.getTitle().startsWith(name)) {
                filteredPlaylists.add(playlist);
            }
        }
        return filteredPlaylists;
    }

    public ArrayList<PlayList> searchAfterOwnerPlaylist(ArrayList<PlayList> playlists) {
        ArrayList<PlayList> filteredPlaylists = new ArrayList<>();
        for(PlayList playlist : playlists) {
            if(playlist.getOwner().equals(owner)) {
                filteredPlaylists.add(playlist);
            }
        }
        return filteredPlaylists;
    }

}
