package uebungen.musicfinder;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;

public class MusicFinder {

    private File start;


    public static void main(String[] args) {//check parameters
        if (args.length != 1) {
            System.out.println("musicfinder start");
            System.exit(1);
        }
        //create new music finder and show dara.
        new MusicFinder(args[0]).findMusic(System.out::println);
    }

    /**
     * create new music finder
     *
     * @param start start directory
     * @throws IllegalArgumentException if there is no start directory
     */
    public MusicFinder(String start) {
        this(new File(start));
    }

    public MusicFinder(File start) {
        if (!start.exists() || !start.isDirectory() || !start.canRead()) {
            throw new IllegalArgumentException("cannot read root directory");
        }
        this.start = start;

    }

    /**
     * find music data
     * @param c Consumer
     */
    public void findMusic(Consumer<File> c) {
        findMusic(start, c);
    }

    private void findMusic(File directory, Consumer<File> c) { //find subdirectories
        File[] unterverzeichnisse = directory.listFiles(f -> f.isDirectory() && f.canRead());
        if (unterverzeichnisse != null) { //search in subd.
            Arrays.stream(unterverzeichnisse).forEach(f -> this.findMusic(f, c));
        }
        //find correct data that ends with .mp3
        File[] musikDateien = directory.listFiles(f -> f.getName().endsWith(".mp3"));
        if (musikDateien != null) {
            Arrays.stream(musikDateien).forEach(c);
        }
    }

}
