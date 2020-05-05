package servlet;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: simple-framework
 * @description:
 * @author: tongkeyon
 * @create:
 **/
@Slf4j
@WebServlet("/hello")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name ="Hello My Simple Framework  :)";
        req.setAttribute("item",name);
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
        log.info("copy");
    }
}
