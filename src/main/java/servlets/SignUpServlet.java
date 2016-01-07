package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Viktor on 24.12.2015.
 */
public class SignUpServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignUpServlet.class);

    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");
        String result_text = "";

        response.setContentType("text/html;charset=utf-8");

        if (login == null || login.isEmpty() /* || pass.isEmpty() */) {
            result_text = "Empty user is not valid.";
            response.getWriter().println(result_text);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            log.info(result_text);

            return;
        }

        UsersDataSet usersDataSet = null;
        try {
            usersDataSet = accountService.getUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (usersDataSet == null) {
            try {
                accountService.addNewUser(login,pass,email);
                result_text = "New user "+login+" has been added.";
            } catch (DBException e) {
                log.error(e);
            }
        }else{
            result_text = "User "+login+" already exists.";
        }

        response.getWriter().println(result_text);
        response.setStatus(HttpServletResponse.SC_OK);

        log.info(result_text);


//        UserProfile profile = accountService.getUserByLogin(login);
//        if (profile == null || !profile.getPass().equals(pass)) {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZ2ED);
//            return;
//        }
//
//        accountService.addSession(request.getSession().getId(), profile);
//        Gson gson = new Gson();
//        String json = gson.toJson(profile);
//        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().println(json);
//        response.setStatus(HttpServletResponse.SC_OK);
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UsersDataSet usersDataSet = accountService.getUserBySessionId(sessionId);
        if (usersDataSet == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

}
