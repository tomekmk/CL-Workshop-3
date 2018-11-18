package pl.coderslab.Filter;

import pl.coderslab.Service.DBService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebFilter(filterName = "BaseFilter", urlPatterns = "/*")
public class BaseFilter implements Filter {
    private static String queryUsers = "SELECT count(id) as quantity FROM users";
    private static String queryGroups = "SELECT count(id) as quantity FROM groups";
    private static String querySolutions = "SELECT count(id) as quantity FROM solutions";
    private static String queryExercises = "SELECT count(id) as quantity FROM exercises";

    public static int getQuantity(PreparedStatement query) throws SQLException {
        int quantity = 0;
        ResultSet rs = query.executeQuery();
        rs.next();
        quantity = rs.getInt("quantity");
        rs.close();
        return quantity;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        try (Connection conn = DBService.getConnForServlet();
             PreparedStatement prep1 = conn.prepareStatement(queryUsers);
             PreparedStatement prep2 = conn.prepareStatement(queryGroups);
             PreparedStatement prep3 = conn.prepareStatement(querySolutions);
             PreparedStatement prep4 = conn.prepareStatement(queryExercises)) {

            req.setAttribute("quantityUsers", getQuantity(prep1));
            req.setAttribute("quantityGroups", getQuantity(prep2));
            req.setAttribute("quantitySolutions", getQuantity(prep3));
            req.setAttribute("quantityExercises", getQuantity(prep4));

        } catch (SQLException e) {
            System.out.println("BaseFilter: " + e.getMessage());
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
