package socketServer;

/**
 * Created by Victor on 17.03.2016.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import java.net.*;

public class ServeOneJabber extends Thread {

    static final Logger logger = LogManager.getLogger(ServeOneJabber.class.getName());

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServeOneJabber(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Включаем автоматическое выталкивание:
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                .getOutputStream())), true);
        // Если любой из вышеприведенных вызовов приведет к
        // возникновению исключения, то вызывающий отвечает за
        // закрытие сокета. В противном случае, нить
        // закроет его.
        start(); // вызываем run()
    }

    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equalsIgnoreCase("Bue.")) {
                    System.out.println("have got Bue.");
                    logger.info("have got Bue.");
                    break;
                }
                out.println(str);
                System.out.println("Echoing: " + str);
                logger.info("Echoing: " + str);
            }
            System.out.println("closing...");
            logger.info("closing...");
        }
        catch (IOException e) {
            System.err.println("IO Exception");
            logger.error(e);
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.err.println("Socket not closed");
                logger.error(e);
            }
        }
    }
}