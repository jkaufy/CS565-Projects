import java.io.*;
import java.net.*;
import java.lang.*;



public class EchoServer {
    public static void main(String[] args) 
    {
        try (ServerSocket server = new ServerSocket(2080))
        {
            while(true)
            {
                Socket connecting = server.accept();
                new Thread(new EchoThread(connecting)).start();
                
            }
        }
        catch (Exception e)
        {System.out.println(e);}
    }
}

class EchoThread implements Runnable {

    private Socket connection;
    InputStream fromClient;
    PrintWriter toClient;

    EchoThread(Socket connecting)
    {
        this.connection = connecting;
    }

    @Override
    public void run ()
    {
        try
        {
            fromClient = connection.getInputStream();
            toClient = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));

            System.out.println("Client Connected");

            while(true)
            {
                char charFromClient = (char)fromClient.read();
                if(Character.isLetter(charFromClient))
                {
                    toClient.println("ECHO: " + charFromClient);
                    toClient.flush();
                    if(charFromClient == 'q')
                    {
                        break;
                    }
                }
            }
            System.out.println("Client Disconnected");
            connection.close();
        }
        catch(Exception e)
        {System.out.println(e);}
    }

}

