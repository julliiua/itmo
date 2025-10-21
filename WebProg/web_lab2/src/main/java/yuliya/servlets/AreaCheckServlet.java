package yuliya.servlets;

import yuliya.model.Point;
import yuliya.service.AreaCheckService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));

            boolean isHit = AreaCheckService.checkHit(x, y, r);
            Point result = new Point(x, y, r, isHit, new Date());

            ServletContext context = getServletContext();
            List<Point> results = (List<Point>) context.getAttribute("results");
            if (results == null) {
                results = new ArrayList<>();
                context.setAttribute("results", results);
            }

            results.add(result);
            request.setAttribute("result", result);
            request.setAttribute("results", results);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }
}