package Server;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Shima Abbasi on 7/19/2016.
 */
public class Server {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}