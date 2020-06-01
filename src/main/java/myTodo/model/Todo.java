package myTodo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class Todo {

    private int id;
    private String name;
    private Date createdDate;
    private String deadline;
    private Status status;
    private int userId;

}
