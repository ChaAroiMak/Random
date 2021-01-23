package uebungen.musicfinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * content of playlist class will be safed in data system, can be read from there
 */
public class Playlist {

    private List<String> songs = new ArrayList<>();

    public void addSong(String pfad) {
        songs.add(pfad);
    }

    public List<String> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    //write playlist content in file
    public void write(File ziel) throws IOException {
        write(new FileWriter(ziel));
    }

    //write playlist content in writer
    public void write(Writer destination) throws IOException {
        try (BufferedWriter buffered = new BufferedWriter(destination)) {
            for (String song : songs) {
                //Der absolute PFad jeder Datei wird in einer eigenen Zeile ausgegeben
                buffered.write(song);
                buffered.newLine();
            }
        }
    }

    //check playlist content
    public int check(){
        int vorher = songs.size();
        songs = songs.stream()
                .filter(song -> new File(song).exists())
                .collect(Collectors.toList());
        return vorher - songs.size();
    }

    //read playlist from file
    public static Playlist read(File quelle) throws IOException{
        return read(new FileReader(quelle));
    }

    //read playlist from reader
    public static Playlist read(Reader quelle) throws IOException{
        try (BufferedReader reader = new BufferedReader(quelle)){
            Playlist playlist = new Playlist();
            //read file, add playlist for every line
            reader.lines().forEach(playlist::addSong);
            return playlist;
        }
    }

    //create playlist out of all mp3s from directory
    public static Playlist ausVerzeichnis(File startVerzeichnis) {
        Playlist playlist = new Playlist();
        new MusicFinder(startVerzeichnis).findMusic(datei -> playlist.addSong(datei.getAbsolutePath()));
        return playlist;
    }
}
