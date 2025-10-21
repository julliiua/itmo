package yuliya.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        if (x != null && y != null && r != null &&
                !x.isEmpty() && !y.isEmpty() && !r.isEmpty()) {
            // Запрос содержит координаты - делегируем AreaCheckServlet
            RequestDispatcher dispatcher = request.getRequestDispatcher("/area-check");
            dispatcher.forward(request, response);
        } else {
            // Запрос без координат - показываем форму
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}