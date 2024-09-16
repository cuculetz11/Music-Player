package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commandPlayer.MusicPlayer;
import commands.*;
import commandsResult.*;
import detaliiPerUser.GeneralStatistics;
import detaliiPerUser.PlayList;
import detaliiPerUser.UsersPage;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);


        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        // TODO add your implementation

        JsonNode Node = objectMapper.readTree(new File(filePathInput));
        //Se citesc comenzile si sunt puse in obiecte.(le am pus in obiecte de tip JsonNode)


        HashMap<String, UsersPage> usersPages = new HashMap<>();
        HashMap<String, MusicPlayer> musicPlayers = new HashMap<>();
        for(UserInput user: library.getUsers()) {
            musicPlayers.put(user.getUsername(), new MusicPlayer());
        }
        PublicPlaylists.reset();
        GeneralStatistics.reset();
        GeneralStatistics.setLibrary(library);
        for(JsonNode navi : Node) {
            String command = navi.get("command").asText();

            if(command.equals("search")) {

                //stiu ca nu era neaparat nevooie de clase pnetru a baga inputul comenzilor adica mergea exectata direct dar av vrut sa am eu totul clar si furmos
                SearchInput searchInput = new SearchInput();
                searchInput.setCommand(command);
                searchInput.handleInput(navi);
                musicPlayers.get(searchInput.getUsername()).wasPodcast(searchInput.getTimestamp());
                musicPlayers.get(searchInput.getUsername()).resetAll();//cand comanda e search se scoate ce e in player
                Searched.reset();
                Searched.setIsSearched(true);
                SearchResult searchResult = searchInput.execute(library,usersPages.get(searchInput.getUsername()));
                // Convertește SearchResult în JsonNode
                JsonNode searchResultNode = objectMapper.valueToTree(searchResult);
                outputs.add(searchResultNode);
            }

            if(command.equals("select")) {
                SelectInput selectInput = new SelectInput();
                selectInput.setCommand(command);
                selectInput.handleInput(navi);
                SelectResult selectResult = selectInput.execute();
                Searched.reset();
                JsonNode selectResultNode = objectMapper.valueToTree(selectResult);
                outputs.add(selectResultNode);

            }

            if(command.equals("load")) {
                LoadInput loadInput = new LoadInput();
                loadInput.setCommand(command);
                loadInput.handleInput(navi);
                LoadOut loadout = loadInput.execute();
                loadInput.loadMusicPlayer( musicPlayers.get(loadInput.getUsername()));
                Selected.reset();
                JsonNode loadResultNode = objectMapper.valueToTree(loadout);
                outputs.add(loadResultNode);
            }
            if(command.equals("playPause")) {
                PlayPauseInput playPauseInput = new PlayPauseInput();
                playPauseInput.setCommand(command);
                playPauseInput.handleInput(navi);
                musicPlayers.get(playPauseInput.getUsername()).VerifyTime(playPauseInput.getTimestamp());
                PlayPauseOut playPauseOut = playPauseInput.execute( musicPlayers.get(playPauseInput.getUsername()));
                JsonNode playPauseResultNode = objectMapper.valueToTree(playPauseOut);
                outputs.add(playPauseResultNode);

            }
            if(command.equals("status")) {
                StatusInput statusInput = new StatusInput();
                statusInput.setCommand(command);
                statusInput.handleInput(navi);
                musicPlayers.get(statusInput.getUsername()).VerifyTime(statusInput.getTimestamp());
                StatusOut statusOut = statusInput.execute( musicPlayers.get(statusInput.getUsername()));
                JsonNode statusResultNode = objectMapper.valueToTree(statusOut);
                outputs.add(statusResultNode);

            }

            if(command.equals("createPlaylist")) {
                Selected.reset();
                CreatePlaylistInput createPlaylistInput = new CreatePlaylistInput();
                createPlaylistInput.setCommand(command);
                createPlaylistInput.handleInput(navi);
                PlaylistOut playlistOut = createPlaylistInput.execute(usersPages);
                JsonNode playlistResultNode = objectMapper.valueToTree(playlistOut);
                outputs.add(playlistResultNode);

            }

            if(command.equals("addRemoveInPlaylist")) {
                Selected.reset();
                addRemoveInPlaylistInput addrmvPlaylistInput = new addRemoveInPlaylistInput();
                addrmvPlaylistInput.setCommand(command);
                addrmvPlaylistInput.handleInput(navi);
                addRmvPlaylistResult addrmvPlaylistResult = addrmvPlaylistInput.execute(usersPages, musicPlayers.get(addrmvPlaylistInput.getUsername()));
                JsonNode addrmvPlaylistResultNode = objectMapper.valueToTree(addrmvPlaylistResult);
                outputs.add(addrmvPlaylistResultNode);
            }

            if(command.equals("like")) {
                Selected.reset();
                LikeInput likeInput = new LikeInput();
                likeInput.setCommand(command);
                likeInput.handleInput(navi);
                musicPlayers.get(likeInput.getUsername()).VerifyTime(likeInput.getTimestamp());
                LikeOut likeOut = likeInput.execute(usersPages,musicPlayers.get(likeInput.getUsername()));
                JsonNode likeResultNode = objectMapper.valueToTree(likeOut);
                outputs.add(likeResultNode);
            }
            if(command.equals("showPlaylists")){
                Selected.reset();
                showPlaylistOut playlistsOut = new showPlaylistOut(command,navi);
                ArrayList<PlayList> playLists = usersPages.get(playlistsOut.getUser()).getPlaylists();
                playlistsOut.addToResult(playLists);
                JsonNode playlistResultNode = objectMapper.valueToTree(playlistsOut);
                outputs.add(playlistResultNode);
            }
            if(command.equals("showPreferredSongs")) {
                Selected.reset();
                showPreferredSongs songs = new showPreferredSongs(command,navi);
                ArrayList<SongInput> likedSongs = usersPages.get(songs.getUser()).getLikedSongs();
                songs.addToResult(likedSongs);
                JsonNode songResultNode = objectMapper.valueToTree(songs);
                outputs.add(songResultNode);
            }
            if(command.equals("repeat")) {
                RepeatOut repeatOut = new RepeatOut(navi,command);

                musicPlayers.get(repeatOut.getUser()).VerifyTime(repeatOut.getTimestamp());

                repeatOut.execute(musicPlayers.get(repeatOut.getUser()));
                JsonNode repeatResultNode = objectMapper.valueToTree(repeatOut);
                outputs.add(repeatResultNode);

            }
            if(command.equals("shuffle")) {
                ShuffleInput shuffleInput = new ShuffleInput();
                shuffleInput.setCommand(command);
                shuffleInput.handleInput(navi,musicPlayers.get(shuffleInput.getUsername()));
                musicPlayers.get(shuffleInput.getUsername()).VerifyTime(shuffleInput.getTimestamp());

                ShuffleOut shuffleOut = shuffleInput.execute(musicPlayers.get(shuffleInput.getUsername()));
                JsonNode shuffleResultNode = objectMapper.valueToTree(shuffleOut);
                outputs.add(shuffleResultNode);
            }

            if(command.equals("next")) {
                NextInput nextInput = new NextInput(navi,command);
                musicPlayers.get(nextInput.getUsername()).VerifyTime(nextInput.getTimestamp());
                String message = nextInput.execute(musicPlayers.get(nextInput.getUsername()));
                CommandOut out = new CommandOut(nextInput.getCommand(),nextInput.getUsername(),nextInput.getTimestamp(),message);
                JsonNode nextResultNode = objectMapper.valueToTree(out);
                outputs.add(nextResultNode);
            }
            if(command.equals("prev")) {
                PrevInput prevInput = new PrevInput(navi,command);
                musicPlayers.get(prevInput.getUsername()).VerifyTime(prevInput.getTimestamp());
                String message = prevInput.execute(musicPlayers.get(prevInput.getUsername()));
                CommandOut out = new CommandOut(prevInput.getCommand(),prevInput.getUsername(),prevInput.getTimestamp(),message);
                JsonNode prevResultNode = objectMapper.valueToTree(out);
                outputs.add(prevResultNode);
            }
            if(command.equals("forward")) {
                ForwardInput forwardInput = new ForwardInput(navi,command);
                musicPlayers.get(forwardInput.getUsername()).VerifyTime(forwardInput.getTimestamp());
                String message = forwardInput.execute(musicPlayers.get(forwardInput.getUsername()));
                CommandOut out = new CommandOut(forwardInput.getCommand(),forwardInput.getUsername(),forwardInput.getTimestamp(),message);
                JsonNode forwardResultNode = objectMapper.valueToTree(out);
                outputs.add(forwardResultNode);
            }
            if(command.equals("backward")) {

                BackwardInput backwardInput = new BackwardInput(navi,command);
                musicPlayers.get(backwardInput.getUsername()).VerifyTime(backwardInput.getTimestamp());
                String message = backwardInput.execute(musicPlayers.get(backwardInput.getUsername()));
                CommandOut out = new CommandOut(backwardInput.getCommand(),backwardInput.getUsername(),backwardInput.getTimestamp(),message);
                JsonNode backwardResultNode = objectMapper.valueToTree(out);
                outputs.add(backwardResultNode);
            }
            if(command.equals("switchVisibility")) {
                Selected.reset();
                SwitchVisibilityInput switchVisibilityInput = new SwitchVisibilityInput(navi,command);
                musicPlayers.get(switchVisibilityInput.getUsername()).VerifyTime(switchVisibilityInput.getTimestamp());
                String message = switchVisibilityInput.execute(usersPages.get(switchVisibilityInput.getUsername()));
                CommandOut out = new CommandOut(switchVisibilityInput.getCommand(),switchVisibilityInput.getUsername(),switchVisibilityInput.getTimestamp(),message);
                JsonNode switchVisibilityResultNode = objectMapper.valueToTree(out);
                outputs.add(switchVisibilityResultNode);
            }
            if(command.equals("follow")) {
                FollowInput followInput = new FollowInput(navi,command);
                musicPlayers.get(followInput.getUsername()).VerifyTime(followInput.getTimestamp());
                String message = followInput.execute();
                CommandOut out = new CommandOut(followInput.getCommand(),followInput.getUsername(),followInput.getTimestamp(),message);
                JsonNode followResultNode = objectMapper.valueToTree(out);
                outputs.add(followResultNode);
            }
            if(command.equals("getTop5Playlists")) {
                int timestamp = navi.get("timestamp").asInt();
                GetTop5PlaylistsOut getTop5PlaylistsOut = new GetTop5PlaylistsOut(command,timestamp);
                getTop5PlaylistsOut.execute();
                JsonNode getTop5PlaylistsResultNode = objectMapper.valueToTree(getTop5PlaylistsOut);
                outputs.add(getTop5PlaylistsResultNode);
            }
            if(command.equals("getTop5Songs")) {
                int timestamp = navi.get("timestamp").asInt();
                GetTop5SongsOut getTop5SongsOut = new GetTop5SongsOut(command,timestamp);
                getTop5SongsOut.execute();
                JsonNode getTop5SongsResultNode = objectMapper.valueToTree(getTop5SongsOut);
                outputs.add(getTop5SongsResultNode);
            }

        }


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}
