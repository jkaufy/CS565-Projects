import java.io.*;
import java.net.*;

/**
 * The EchoServer Class creates a simple multithreaded socket 
 * server on the localhost using port 2080.
 */
public class EchoServer {
    public static void main(String[] args) 
    {
        // tries to create a socket server on port 2080 (localhost ip)
        try (ServerSocket server = new ServerSocket(2080))
        {
            // server stays on until the program is manually ended
            while(true)
            {
                // accept any incomming clients connection
                Socket connecting = server.accept();
                // create a new thread containing the the connection between
                // the client and the server using EchoThread
                new Thread(new EchoThread(connecting)).start();
                
            }
        }
        // catches any errors that might occurs and prints them to the terminal
        catch (Exception e)
        {System.out.println(e);}
    }
}


/**
 * The EchoThread class implements runnalbe and handles the 
 * thread interaction between the server and the client. Upon 
 * the character sequence of "quit" in upper or lowercase the 
 * connection will terminate.
 */
class EchoThread implements Runnable {

    // Socket varaible to hold the connection
    private Socket connection;

    // Holds the input stream from the client
    InputStream fromClient;

    // Holds the output stream to the client
    PrintWriter toClient;

    /**
     * Echothread constructor takes in a socket connection and
     * sets the private variable connection.
     */
    EchoThread(Socket connecting)
    {
        this.connection = connecting;
    }


    @Override
    public void run ()
    {
        // tries to read and write in data to the client
        try
        {
            // intializes the input and output variables with the connection socket
            fromClient = connection.getInputStream();
            toClient = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));

            // initializes disconnect to zero. If disconnect is 5 we terminate the connection
            int disconnect = 0;

            // print to terminal for debugging purposes
            System.out.println("Client Connected");

            // while we are connected to the client
            while(true)
            {
                // grabs the next character from the client
                char charFromClient = (char)fromClient.read();

                // if the character is a letter
                if(Character.isLetter(charFromClient))
                {
                    // sends to the char we read in back to the client
                    toClient.println("ECHO: " + charFromClient);
                    toClient.flush();

                    // we use this switch case to check for the character
                    // sequence "quit" in upper or lower case.
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

                    // if we succesfully read in the character sequence "quit" disconnect
                    if(disconnect == 4)
                    {
                        break;
                    }
                }
            }
            // print to terminal for debugging purposes
            System.out.println("Client Disconnected");

            // closes the connection
            connection.close();
        }
        // catches any errors that might occurs and prints them to the terminal
        catch(Exception e)
        {System.out.println(e);}
    }

}

