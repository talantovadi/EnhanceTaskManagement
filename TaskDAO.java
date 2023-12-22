package com.example.taskmanagement;

import javafx.collections.ObservableList;

import java.sql.*;

public class TaskDAO {
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/java";
    private String username = "postgres";
    private String password = "21442139";

    HomeworkTask ht = new HomeworkTask();
    MeetingTask mt = new MeetingTask();
    ShoppingTask st = new ShoppingTask();

    public void create(String type, String name, String description, Date deadline, Priority priority, boolean completed) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(
                    "insert into tasks (type, name, description, deadline, priority, completed) " +
                    "select ?, ?, ?, ?, ?, ? " +
                    "where not exists (select 1 from tasks where type = ? and name = ? and description = ?) ");
            statement.setString(1, type);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setDate(4, deadline);
            statement.setString(5, String.valueOf(priority));
            statement.setBoolean(6, completed);
            statement.setString(7, type);
            statement.setString(8, name);
            statement.setString(9, description);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
    public void read(ObservableList<Task> tasks) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tasks");
            while (resultSet.next()){
                String type = resultSet.getString("type");
                String name = resultSet.getString("name");
                if(type.equals("Homework task")){
                    ht = new HomeworkTask(name);
                    ht.setTaskType(resultSet.getString("type"));
                    ht.setTaskName(resultSet.getString("name"));
                    ht.setTaskDescription(resultSet.getString("description"));
                    ht.setDeadline(resultSet.getDate("deadline"));
                    ht.setPriority(Priority.valueOf(resultSet.getString("priority")));
                    ht.markAsComplete(resultSet.getBoolean("completed"));
                    tasks.add(ht);
                }
                else if (type.equals("Meeting task")){
                    mt = new MeetingTask(name);
                    mt.setTaskType(resultSet.getString("type"));
                    mt.setTaskName(resultSet.getString("name"));
                    mt.setTaskDescription(resultSet.getString("description"));
                    mt.setDeadline(resultSet.getDate("deadline"));
                    mt.setPriority(Priority.valueOf(resultSet.getString("priority")));
                    mt.markAsComplete(resultSet.getBoolean("completed"));
                    tasks.add(mt);
                }
                else if (type.equals("Shopping task")){
                    st = new ShoppingTask(name);
                    st.setTaskType(resultSet.getString("type"));
                    st.setTaskName(resultSet.getString("name"));
                    st.setTaskDescription(resultSet.getString("description"));
                    st.setDeadline(resultSet.getDate("deadline"));
                    st.setPriority(Priority.valueOf(resultSet.getString("priority")));
                    st.markAsComplete(resultSet.getBoolean("completed"));
                    tasks.add(st);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void update(String type, String name, String description, Date deadline, Priority priority, boolean completed) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(
                    "update tasks " +
                    "set type = ?, description = ?, deadline = ?, priority = ?, completed = ? " +
                    "where name = ? ");
            statement.setString(1, type);
            statement.setString(2, description);
            statement.setDate(3, deadline);
            statement.setString(4, String.valueOf(priority));
            statement.setBoolean(5, completed);
            statement.setString(6, name);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
    public void delete(String type, String name) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("delete from tasks where type = ? and name = ?");
            statement.setString(1, type);
            statement.setString(2, name);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
