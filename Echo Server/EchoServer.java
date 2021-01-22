import java.io.*;
import java.net.*;

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
            int disconnect = 0;
            System.out.println("Client Connected");

            while(true)
            {
                char charFromClient = (char)fromClient.read();
                if(Character.isLetter(charFromClient))
                {
                    toClient.println("ECHO: " + charFromClient);
                    toClient.flush();

                    switch (disconnect)
                    {
                        case 0:
                            if(Character.toUpperCase(charFromClient) == 'Q')
                                disconnect ++;
                            else
                                disconnect = 0;
                            break;
                        case 1:
                            if(Character.toUpperCase(charFromClient) == 'U')
                                disconnect ++;
                            else
                                disconnect = 0;
                            break;
                        case 2:
                            if(Character.toUpperCase(charFromClient) == 'I')
                                disconnect ++;
                            else
                                disconnect = 0;
                            break;
                        case 3:
                            if(Character.toUpperCase(charFromClient) == 'T')
                                disconnect ++;
                            else
                                disconnect = 0;
                            break;
                        default:
                            disconnect = 0;
                            break;
                    }

                    if(disconnect == 4)
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

