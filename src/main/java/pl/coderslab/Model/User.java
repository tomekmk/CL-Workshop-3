package pl.coderslab.Model;

import pl.coderslab.Service.BCrypt;
import pl.coderslab.Service.DBService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private Integer id = 0;
    private String username = "";
    private String email = "";
    private String password = "";
    private Group userGroup = null;


    public void saveToDB(Connection conn) throws SQLException {        //create and update

        if (this.id == 0) {
            String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?, ?, ?, ?)";
            String[] generatedColumns = {"id"};
            PreparedStatement prepStat = conn.prepareStatement(sql, generatedColumns);
            prepStat.setString(1, this.username);
            prepStat.setString(2, this.email);
            prepStat.setString(3, this.password);
            prepStat.setInt(4, this.userGroup.getId());
            prepStat.executeUpdate();
            ResultSet rs = prepStat.getGeneratedKeys();
            if (rs.next())
                this.id = rs.getInt(1);
            rs.close();
            prepStat.close();
        } else {
            String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? where id = ?";
            PreparedStatement prepStat = conn.prepareStatement(sql);
            prepStat.setString(1, this.username);
            prepStat.setString(2, this.email);
            prepStat.setString(3, this.password);
            prepStat.setInt(4, this.userGroup.getId());
            prepStat.setInt(5, this.id);
            prepStat.executeUpdate();
            prepStat.close();
        }
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1, this.id);
            prepStat.executeUpdate();
            this.id = 0;
            prepStat.close();
        }
    }

    static public User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement prepStat = conn.prepareStatement(sql);
        prepStat.setInt(1, id);
        ResultSet resultSet = prepStat.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            loadedUser.setUserGroupId(resultSet.getInt("user_group_id"));
            return loadedUser;
        }
        resultSet.close();
        prepStat.close();
        return null;
    }

    static public User[] loadAllUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        PreparedStatement prepStat = conn.prepareStatement(sql);
        ResultSet resultSet = prepStat.executeQuery();
        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            loadedUser.setUserGroupId(resultSet.getInt("user_group_id"));
            users.add(loadedUser);
        }
        User[] uArray = new User[users.size()];
        uArray = users.toArray(uArray);
        resultSet.close();
        prepStat.close();
        return uArray;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setUserGroupId(Integer userGroupId) {
        try (Connection conn = DBService.getConnection()) {
            this.userGroup = Group.loadGroupById(conn, userGroupId);
        } catch (SQLException e) {}
    }


    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserGroupId() {
        return userGroup.getId();
    }

    public Group getUserGroup() {
        return userGroup;
    }
}
