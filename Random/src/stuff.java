import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class stuff {

    List<Integer> list = new List;
    void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("memento.txt");
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeInt(list.size());

        for(int value : list) {
            dos.writeInt(value);
        }
    }

    void restore() throws IOException {
        FileInputStream fis = new FileInputStream("memento.txt");
        DataInputStream dis = new DataInputStream(fis);
        int length = dis.writeInt();
        list = new ArrayList<>();

        for(; length > 0; length--) {
            list.add(dis.readInt());
        }
    }

}
