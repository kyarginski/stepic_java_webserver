package main;

import accounts.AccountService;
import accounts.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AllRequestsServlet;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import servlets.SessionsServlet;
import servlets.UsersServlet;


/**
 * @author Victor Kyarginski
 *         <p>
 *         Java: Запуск сервера – Шаг 12
 *         <p> *
 *         Задача:
 *
        Написать сервер с двумя сервлетами:
        SignUpServlet для обработки запросов на signup и
        SignInServlet для обработки запросов на signin

        Сервлеты должны слушать POST запросы с параметрами
        login
        password

 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class);


    public static void main(String[] args) throws Exception {

        log.info("Server started");

        AccountService accountService = new AccountService();

        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UsersServlet(accountService)), "/api/v1/users");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        server.join();    }
}
