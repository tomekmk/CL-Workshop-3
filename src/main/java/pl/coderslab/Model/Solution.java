package pl.coderslab.Model;

import pl.coderslab.Service.DBService;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
    private Integer id = 0;
    private Timestamp created = null;
    private Timestamp updated = null;
    private String description = "";
    private Exercise exercise = null;        //do przeróbki
    private User user = null;            //do przeróbki

    public void saveToDB(Connection conn) throws SQLException {        //create and update
        if (this.id == 0) {
            String sql = "INSERT INTO solutions(description, exercise_id, user_id) VALUES (?, ?, ?)";
            String[] generatedColumns = {"id"};
            PreparedStatement prepStat = conn.prepareStatement(sql, generatedColumns);
            prepStat.setString(1, this.description);
            prepStat.setInt(2, this.getExercise_id());
            prepStat.setInt(3, this.getUser_id());
            prepStat.executeUpdate();
            ResultSet rs = prepStat.getGeneratedKeys();
            if (rs.next())
                this.id = rs.getInt(1);
            rs.close();
            prepStat.close();
        } else {
            String sql = "UPDATE solutions SET updated=current_timestamp, description=?, exercise_id=?, user_id=? where id = ?";
            PreparedStatement prepStat = conn.prepareStatement(sql);
            prepStat.setString(1, this.description);
            prepStat.setInt(2, this.getExercise_id());
            prepStat.setInt(3, this.getUser_id());
            prepStat.setInt(4, this.id);
            prepStat.executeUpdate();
            prepStat.close();
        }
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solutions WHERE id=?";
            PreparedStatement prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1, this.id);
            prepStat.executeUpdate();
            this.id = 0;
            prepStat.close();
        }
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solutions where id=?";
        PreparedStatement prepStat = conn.prepareStatement(sql);
        prepStat.setInt(1, id);
        ResultSet resultSet = prepStat.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.setExercise_id(resultSet.getInt("exercise_id"));
            loadedSolution.setUser_id(resultSet.getInt("user_id"));
            return loadedSolution;
        }
        resultSet.close();
        prepStat.close();
        return null;
    }

    static public Solution[] loadAllSolutions(Connection conn, Integer user_id, Integer limit) throws SQLException {
        String sql = "";
        if (user_id == null)
            sql = "SELECT * FROM solutions ORDER BY created DESC";
        else
            sql = "SELECT * FROM solutions WHERE user_id = ? ORDER BY created DESC";

        if (limit != null)
            sql = sql + " LIMIT " + limit;

        ArrayList<Solution> solutions = new ArrayList<>();
        PreparedStatement prepStat = conn.prepareStatement(sql);
        if (user_id != null)
            prepStat.setInt(1, user_id);
        ResultSet resultSet = prepStat.executeQuery();

        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.setExercise_id(resultSet.getInt("exercise_id"));
            loadedSolution.setUser_id(resultSet.getInt("user_id"));
            solutions.add(loadedSolution);
        }
        Solution[] sArray = new Solution[solutions.size()];
        sArray = solutions.toArray(sArray);
        resultSet.close();
        prepStat.close();
        return sArray;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExercise_id(Integer exercise_id) {
        try (Connection conn = DBService.getConnection()) {
            this.exercise = Exercise.loadExerciseById(conn, exercise_id);
        } catch (SQLException e) {}
    }

    public void setUser_id(Integer user_id) {
        try (Connection conn = DBService.getConnection()) {
            this.user = User.loadUserById(conn, user_id);
        } catch (SQLException e) {}
    }


    public Integer getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public Integer getExercise_id() {
        return exercise.getId();
    }

    public Integer getUser_id() {
        return user.getId();
    }

    public Exercise getExercise() {
        return exercise;
    }

    public User getUser() {
        return user;
    }
}

