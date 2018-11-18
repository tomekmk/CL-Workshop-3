package pl.coderslab.Controller.User;

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

@WebServlet(name = "UserListController", urlPatterns = "/users/list")
public class UserListController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBService.getConnForServlet()) {
            User[] users = User.loadAllUsers(conn);
            request.setAttribute("users", users);
        } catch (SQLException e) {
            System.out.println("UserListController: " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/users/user_list.jsp").forward(request, response);
    }
}
