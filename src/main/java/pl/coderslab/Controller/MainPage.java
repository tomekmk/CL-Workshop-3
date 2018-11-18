package pl.coderslab.Controller;

import pl.coderslab.Model.Solution;
import pl.coderslab.Service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "MainPage", urlPatterns = "/index")
public class MainPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (Connection conn = DBService.getConnForServlet()) {
            Solution[] solutions = Solution.loadAllSolutions(conn, null, 5);
            request.setAttribute("solutions", solutions);
        } catch (Exception e) {
            System.out.println("MainPage: " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/mainpage/mainpage.jsp").forward(request, response);
    }
}