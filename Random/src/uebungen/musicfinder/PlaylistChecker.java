package uebungen.musicfinder;

import java.io.File;

/**
 * check playlist files
 */
public class PlaylistChecker {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) { //check paramters
            throw new Exception("Wrong paramteres");
        }

        File playlistDatei = new File(args[0]);
        if (!playlistDatei.exists() || !playlistDatei.isFile() || !playlistDatei.canRead()) {
            endWithError(args[0] + " is not readable ");
        }

        Playlist playlist = Playlist.read(playlistDatei); //read playlist from file
        int deleted = playlist.check(); //delete
        System.out.println(deleted + " delete files "); //check non existing files
        playlist.schreibe(playlistDatei); //write new playlist

    }

    private static void endWithError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
