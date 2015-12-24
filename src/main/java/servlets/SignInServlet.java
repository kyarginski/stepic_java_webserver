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
public class SignInServlet  extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String result_text = "Unauthorized";
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        response.setContentType("text/html;charset=utf-8");

        if (login == null || pass == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(result_text);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
//        if (profile == null || !profile.getPass().equals(pass)) {
        if (profile == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(result_text);
            return;
        }

//        accountService.addSession(request.getSession().getId(), profile);
//        Gson gson = new Gson();
//        String json = gson.toJson(profile);
//        response.getWriter().println(json);

        result_text = "Authorized";
        response.getWriter().println(result_text);
        response.setStatus(HttpServletResponse.SC_OK);

    }

}