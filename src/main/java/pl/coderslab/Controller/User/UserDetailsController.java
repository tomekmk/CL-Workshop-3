package pl.coderslab.Controller.User;

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

@WebServlet(name = "UserDetailsController", urlPatterns = "/users/details")
public class UserDetailsController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBService.getConnForServlet()) {
            Integer user_id = Integer.parseInt(request.getParameter("user_id"));
            Solution[] solutions = Solution.loadAllSolutions(conn, user_id, null);
            User user = User.loadUserById(conn, user_id);

            request.setAttribute("user", user);
            request.setAttribute("solutions", solutions);
        } catch (SQLException e) {
            System.out.println("UserDetailsController: " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/users/user_details.jsp").forward(request, response);
    }
}
