package myTodo.manager;

import myTodo.db.DbConnectionProvider;
import myTodo.model.Status;
import myTodo.model.Todo;
import myTodo.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TodoManager {

    private Connection connection;

    public TodoManager() {
        connection = DbConnectionProvider.getInstance().getConnection();
    }

    public void addTodo(Todo todo) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo(name,deadline,user_id) Values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setString(2, todo.getDeadline());
        preparedStatement.setInt(3, todo.getUserId());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);
        }
    }

    public void printTodo(int userid) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE user_id = ?");
        preparedStatement.setInt(1, userid);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Todo> todoList = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getDate("createdDate"));
            todo.setDeadline(resultSet.getString("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todoList.add(todo);

            for (Todo todo1 : todoList) {
                System.out.println(todo1);
            }
        }
    }

    public void printAllProgressTodos (int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE status = ? AND user_id = ?");
        preparedStatement.setString(1, String.valueOf(Status.IN_PROGRESS));
        preparedStatement.setInt(2, userId);


        ResultSet resultSet = preparedStatement.executeQuery();

        List<Todo> todoList = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getDate("createdDate"));
            todo.setDeadline(resultSet.getString("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todoList.add(todo);

            for (Todo todo1 : todoList) {
                System.out.println(todo1);
            }
        }
    }

    public void printAllFinishedTodos (int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE status = ? AND user_id = ?");
        preparedStatement.setString(1, String.valueOf(Status.FINISHED));
        preparedStatement.setInt(2, userId);


        ResultSet resultSet = preparedStatement.executeQuery();

        List<Todo> todoList = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getDate("createdDate"));
            todo.setDeadline(resultSet.getString("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todoList.add(todo);

            for (Todo todo1 : todoList) {
                System.out.println(todo1);
            }
        }
    }

    public void changeTodoStatus(int todoId ,String status,int userID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todo SET status = ? WHERE id = ? AND user_id = ?");
        preparedStatement.setString(1, status );
        preparedStatement.setInt(2, todoId);
        preparedStatement.setInt(3, userID);
        preparedStatement.executeUpdate();
    }

    public void deleteTodoById (int todoId,int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id = ? AND user_id = ?");
        preparedStatement.setInt(1,todoId);
        preparedStatement.setInt(2,userId);
        preparedStatement.executeUpdate();
    }
}

