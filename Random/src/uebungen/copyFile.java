package uebungen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.server.ExportException;
import java.util.InputMismatchException;

public class copyFile {
    public static void main(String []args) throws IOException {
        if(args.length != 2) {
            throw new IOException("2 Dateien angeben"); //check if we got correct parameters
        }
        try { //if everything is correct: create new files
            File start = new File(args[0]);
            File end = new File(args[1]);
            checkStartFile(start);

            end = checkAndCreateDestination(end, start.getName());
            File copy = Files.copy(start.toPath(), end.toPath()).toFile();
        }
        catch(Exception e) {
            System.out.println("Error");
        }
    }

    private static void checkStartFile(File start) throws Exception { //check if everything is okay with source file
        if(!start.exists()) {
            throw new IOException("no source file");
        }
        else if (!start.isFile()) {
            throw new IllegalArgumentException("That is not a file");
        }
        else if(!start.canRead()){
            throw new Exception("Cannot read file");
        }
    }

    private static File checkAndCreateDestination(File end, String name) throws Exception {
        if(end.exists() && end.isFile()) {
            throw new Exception("destination file already exists");
        }
        else if (end.exists() && end.isDirectory()) {
            if(!end.canWrite()) {
                throw new Exception("cannot write in destination");
            }
            else {
                return new File(end, name);
            }
        }
        else {
            //destination doesn't exist
            end.getParentFile().mkdirs();
            return end;
        }
    }
}
