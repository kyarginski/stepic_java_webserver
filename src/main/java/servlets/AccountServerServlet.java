package servlets;

import accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author a.akbashev
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountServerServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AccountServerServlet.class.getName());
    public static final String PAGE_URL = "/admin";
    private final AccountServerI accountServer;

    public AccountServerServlet(AccountServerI accountServer) {
        this.accountServer = accountServer;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        int limit = accountServer.getUsersLimit();
        int count = accountServer.getUsersCount();

        logger.info("Limit: {}. Count {}", limit, count);

        if (limit > count) {
            logger.info("User pass");
            accountServer.addNewUser();
        } else{
            logger.info("User reject");
        }

        //При получении GET запроса возвращать значение usersLimit.
        response.getWriter().print(String.valueOf(limit));
        response.setStatus(HttpServletResponse.SC_OK);

//        String remove = request.getParameter("remove");
//
//        if (remove != null) {
//            accountServer.removeUser();
//            response.getWriter().println("Hasta la vista!");
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//
//        int limit = accountServer.getUsersLimit();
//        int count = accountServer.getUsersCount();
//
//        logger.info("Limit: {}. Count {}", limit, count);
//
//        if (limit > count) {
//            logger.info("User pass");
//            accountServer.addNewUser();
//            response.getWriter().println("Hello, world!");
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            logger.info("User were rejected");
//            response.getWriter().println("Server is closed for maintenance!");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
    }
}
