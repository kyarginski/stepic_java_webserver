package servlets;

import resourceServer.ResourceServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resourceServer.ResourceServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServerServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ResourceServerServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private ResourceServerI resourceServer;

    public ResourceServerServlet(ResourceServerI resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String path_to_resource = request.getParameter("path");

        response.setContentType("text/html;charset=utf-8");

        if (path_to_resource == null || path_to_resource.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.info("Нет данных о xml-файле");
        } else {

            resourceServer = new ResourceServer(path_to_resource);
            logger.info(resourceServer);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
