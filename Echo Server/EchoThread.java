import java.io.*;
import java.net.*;

public class EchoThread extends Thread {

    protected Socket connection;

    public EchoThread(Socket connecting)
    {
        this.connection = connecting;
    }

    @Override
    public void run ()
    {
        try
        {

            BufferedReader inData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter echo = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));

            while(true)
            {
                String str = inData.readLine();
                str = str.toLowerCase();
                if(str != null)
                {
                    echo.println("ECHO: " + str);
                    echo.flush();
                    if(str.trim().equals("quit"))
                    {
                        break;
                    }
                }
            }
            connection.close();
        }
        catch(Exception e)
        {}
    }

}