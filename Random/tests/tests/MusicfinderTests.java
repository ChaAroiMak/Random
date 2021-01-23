package tests;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import uebungen.musicfinder.Playlist;

public class MusicfinderTests {

    public MusicfinderTests() {
    }

    @Test
    public void testAddUndGet() {
        Playlist testeMich = new Playlist();
        testeMich.addSong("/tmp/test1.mp3");
        testeMich.addSong("/tmp/test2.mp3");
        List<String> inhalt = testeMich.getSongs();
        assertEquals(0, inhalt.indexOf("/tmp/test1.mp3"));
        assertEquals(1, inhalt.indexOf("/tmp/test2.mp3"));
    }

    @Test
    public void testWrite() throws IOException{
        Playlist testeMe = new Playlist();
        testeMe.addSong("/tmp/test1.mp3");
        testeMe.addSong("/tmp/test2.mp3");
        StringWriter writer = new StringWriter();
        testeMe.write(writer);
        assertEquals("/tmp/test1.mp3\r\n/tmp/test2.mp3\r\n", writer.toString());
    }

    @Test
    public void testFromDirectory() throws IOException{
        File directory = createTempDirectory(null);
        File mp3 = erzeugeTempFile(directory, ".mp3");
        File nichtMp3 = erzeugeTempFile(directory, ".txt");
        File unterverzeichnis = createTempDirectory(directory);
        File mp3inUnterverzeichnis = erzeugeTempFile(unterverzeichnis, ".mp3");

        Playlist testeMe = Playlist.ausVerzeichnis(directory);
        List<String> inhalt = testeMe.getSongs();
        assertEquals(2, inhalt.size());
        assertTrue(inhalt.contains(mp3.getAbsolutePath()));
        assertTrue(inhalt.contains(mp3inUnterverzeichnis.getAbsolutePath()));
    }

    @Test
    public void testLese() throws IOException{
        Reader quelle = new StringReader("/tmp/test1.mp3\n/tmp/test2.mp3\n");
        Playlist testeMe = Playlist.read(quelle);
        List<String> inhalt = testeMe.getSongs();
        assertEquals(0, inhalt.indexOf("/tmp/test1.mp3"));
        assertEquals(1, inhalt.indexOf("/tmp/test2.mp3"));
    }

    @Test
    public void testVerifiziere() throws IOException{
        File verzeichnis = createTempDirectory(null);
        File mp3 = erzeugeTempFile(verzeichnis, ".mp3");
        File notMp3 = erzeugeTempFile(verzeichnis, ".txt");
        File unterverzeichnis = createTempDirectory(verzeichnis);
        File mp3inUnterverzeichnis = erzeugeTempFile(unterverzeichnis, ".mp3");

        Playlist testeMe = Playlist.ausVerzeichnis(verzeichnis);
        testeMe.addSong(new File(verzeichnis + "notexist.mp3").getAbsolutePath());
        assertEquals(1, testeMe.check());
        List<String> inhalt = testeMe.getSongs();
        assertEquals(2, inhalt.size());
        assertTrue(inhalt.contains(mp3.getAbsolutePath()));
        assertTrue(inhalt.contains(mp3inUnterverzeichnis.getAbsolutePath()));
    }

    private File createTempDirectory(File parent) throws IOException{
        File directory;
        if (parent == null){
            directory = Files.createTempDirectory(getClass().getName()).toFile();
        } else {
            directory = Files.createTempDirectory(parent.toPath(), getClass().getName()).toFile();
        }
        directory.deleteOnExit();
        return directory;
    }

    private File erzeugeTempFile(File parent, String endung) throws IOException{
        File datei = File.createTempFile(getClass().getName(), endung, parent);
        datei.deleteOnExit();
        return datei;
    }
}
