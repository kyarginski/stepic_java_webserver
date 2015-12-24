package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Viktor on 24.12.2015.
 */
public class SignUpServlet extends HttpServlet {

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

        if (login == null || pass == null) {
            result_text = "Empty user or password are not valid.";
            response.getWriter().println(result_text);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null) {
            accountService.addNewUser(new UserProfile(login,pass,email));
            result_text = "New user "+login+" has been added.";
        }else{
            result_text = "User "+login+" has been log in.";
        }

        response.getWriter().println(result_text);
        response.setStatus(HttpServletResponse.SC_OK);


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
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
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
