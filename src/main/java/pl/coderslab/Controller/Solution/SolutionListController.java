package pl.coderslab.Controller.Solution;

import pl.coderslab.Model.Solution;
import pl.coderslab.Model.User;
import pl.coderslab.Service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "SolutionListController", urlPatterns = "/solutions/list")
public class SolutionListController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBService.getConnForServlet()) {
            Solution[] solutions = Solution.loadAllSolutions(conn, null, null);
            request.setAttribute("solutions", solutions);
        } catch (SQLException e) {
            System.out.println("SolutionListController: " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/solutions/solutions_list.jsp").forward(request, response);

    }
}
