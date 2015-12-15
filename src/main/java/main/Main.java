package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AllRequestsServlet;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * @author Victor Kyarginski
 *         <p>
 *         Java: Запуск сервера – Шаг 12
 *         <p> *
 *         Задача:
 *
 *         Написать сервлет, который будет обрабатывать запросы на /mirror
 *         При получении GET запроса с параметром key=value сервлет должен вернуть в response строку содержащую value.
 *
 *         Например, при GET запросе /mirror?key=hello сервер должен вернуть страницу, на которой есть слово "hello".
 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class);


    public static void main(String[] args) throws Exception {

        log.info("Server started");

        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/mirror");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
