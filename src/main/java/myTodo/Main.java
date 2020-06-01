package myTodo;

import myTodo.manager.TodoManager;
import myTodo.manager.UserManager;
import myTodo.model.Status;
import myTodo.model.Todo;
import myTodo.model.User;

import java.sql.SQLException;
import java.util.Scanner;


public class Main implements Commands {

    private static final Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static TodoManager todoManager = new TodoManager();
    private static User currentUser;

    public static void main(String[] args) {

        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                case EXIT:
                    isRun = false;
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }

    }

    private static void registerUser() {

        System.out.println("Please input user data: " +
                "name,surname,email,password");
        try {
            String userDataStr = scanner.nextLine();
            String[] userData = userDataStr.split(",");
            User user = new User();
            user.setName(userData[0]);
            user.setSurname(userData[1]);
            user.setEmail(userData[2]);
            user.setPassword(userData[3]);
            User userByEmail = userManager.getUserByEmail(userData[2]);
            if (userByEmail != null) {
                System.out.println("User already exists");
            } else {
                userManager.addUser(user);
                currentUser = user;
            }
        } catch (ArrayIndexOutOfBoundsException | SQLException e) {
            System.out.println("Wrong Data!");
        }

    }

    private static void loginUser() {
        System.out.println("Please input email,password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArray = loginStr.split(",");
            User userByEmailAndPassword = userManager.getUserByEmailAndPassword(loginArray[0], loginArray[1]);
            if (userByEmailAndPassword != null) {
                currentUser = userByEmailAndPassword;
                System.out.println("Welcome " + currentUser.getName());
                loginSuccess();
            }else {
                System.out.println("Incorrect email or password");
            }
        } catch (ArrayIndexOutOfBoundsException | SQLException e) {
            System.out.println("Wrong data!");
        }

    }

    private static void loginSuccess() {
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case MY_LIST:
                    myTodo();
                    break;
                case MY_IN_PROGRESS_LIST:
                    progressPrint();
                    break;
                case MY_FINISHED_LIST:
                    finishedPrint();
                    break;
                case ADD_TODO:
                    addTodo();
                    break;
                case CHANGE_TODO_STATUS:
                    changeTodoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteTodoById();
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }
    }

    private static void deleteTodoById() {
        myTodo();
        System.out.println("Input todo id");
        String deleteId = scanner.nextLine();
        try {
            todoManager.deleteTodoById(Integer.parseInt(deleteId),currentUser.getId());
            System.out.println("Todo was deleted");
        } catch (SQLException e) {
            System.out.println("Todo not deleted!");
        }

    }

    private static void changeTodoStatus() {
        myTodo();
        System.out.println("Input todo id");
        String todoId = scanner.nextLine();
        System.out.println("Input status " + Status.IN_PROGRESS + " or " + Status.FINISHED );
        String todoStatus =  scanner.nextLine();
        try {
            todoManager.changeTodoStatus(Integer.parseInt(todoId),todoStatus.toUpperCase(),currentUser.getId());
            System.out.println("Status was updated!");
        } catch (SQLException e) {
            System.out.println("Todo not updated!");
        }
    }

    private static void finishedPrint() {
        try {
            todoManager.printAllFinishedTodos(currentUser.getId());
        } catch (SQLException e) {
            System.out.println("You do not have finished todo!");
        }
    }

    private static void progressPrint() {
        try {
            todoManager.printAllProgressTodos(currentUser.getId());
        } catch (SQLException e) {
            System.out.println("You do not have progress todo!");
        }
    }

    private static void myTodo() {
        try {
            todoManager.printTodo(currentUser.getId());
        } catch (SQLException e) {
            System.out.println("You do not have todo!");
        }
    }

    private static void addTodo() {
        System.out.println("Please input todo data: (name,YYYY-MM-DD,)");
        try {
            String todoDataStr = scanner.nextLine();
            String[] todoData = todoDataStr.split(",");
            Todo todo = new Todo();
            todo.setName(todoData[0]);
            todo.setDeadline(todoData[1]);
            todo.setUserId(currentUser.getId());
            todoManager.addTodo(todo);
            System.out.println("Todo was added!");
        } catch (ArrayIndexOutOfBoundsException | SQLException e) {
            System.out.println("Wrong Data!");
        }
    }
}
