package uebungen.musicfinder;

import java.io.File;
import java.io.IOException;

/**
 * create playlist from directory
 */
public class PlaylistWriter {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            endWithError(" Error ");
        }
        File musicdirectory = new File(args[0]);
        if (!musicdirectory.exists() || !musicdirectory.isDirectory()) {
            endWithError(args[0] + " is no directory");
        }
        File outputFile = new File(args[1]);
        if (outputFile.exists()) {
            endWithError(args[1] + " already exists");
        }

        //create playlist
        Playlist found = Playlist.ausVerzeichnis(musicdirectory);
        if (found.getSongs().size() == 0) {
            //no data found
            System.out.println("no files found: " + args[0]);
        } else {
            //found files, write list
            System.out.println(found.getSongs().size() + " MP3 found in " + args[0]);
            found.schreibe(outputFile);
        }

    }

    private static void endWithError(String message) {
        System.err.println(message);
        System.exit(1);
    }

}
