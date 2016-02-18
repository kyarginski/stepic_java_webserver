package main;

import accountServer.AccountServer;
import accountServer.AccountServerController;
import accountServer.AccountServerControllerMBean;
import accountServer.AccountServerI;
import accounts.AccountService;
import accounts.UserProfile;
import chat.WebSocketChatServlet;
import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


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

    При получении POST запроса на signup сервлет SignUpServlet должн запомнить логин и пароль в AccountService.
    После этого польователь с таким логином считается зарегистрированным.
    При получении POST запроса на signin, после регистрации, SignInServlet проверяет,
    логин/пароль пользователя. Если пользователь уже зарегистрирован, север отвечает

    Status code (200)
    и текст страницы:
    Authorized

    если нет:
    Status code (401)
    текст страницы:
    Unauthorized

 */
public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);


    public static void main(String[] args) throws Exception {

        AccountService accountService = new AccountService();

        AccountServerI accountServer = new AccountServer(10);

//        accountService.addNewUser(new UserProfile("admin"));
//        accountService.addNewUser(new UserProfile("test"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UsersServlet(accountService)), "/api/v1/users");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");

        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");

        context.addServlet(new ServletHolder(new AccountServerServlet(accountServer)), AccountServerServlet.PAGE_URL);


        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
        mbs.registerMBean(serverStatistics, name);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        log.info("Server started");

        server.start();
        server.join();    }
}
