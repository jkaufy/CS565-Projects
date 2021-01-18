import java.io.*;
import java.net.*;



public class MultiThreadedEchoServer {
    public static void main(String[] args) 
    {
        try
        {
            ServerSocket server = new ServerSocket(2080);
            while(true)
            {
                Socket connecting = server.accept();
                new EchoThread(connecting).start();
            }
        }
        catch (Exception e)
        {}
    }
}

