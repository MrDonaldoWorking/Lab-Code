package ru.akirakozov.sd.mvc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {
    public enum TaskStatus {
        ACTIVE,
        DONE
    }

    private int taskId;
    private int listId;
    private String description;
    private int status;

    public Task() {}

    public Task(int taskId, int listId, String description) {
        this.taskId = taskId;
        this.listId = listId;
        this.description = description;
        this.status = TaskStatus.ACTIVE.ordinal();
    }

    public Task(int taskId, int listId, String description, int status) {
        this.taskId = taskId;
        this.listId = listId;
        this.description = description;
        this.status = status;
    }

    public void markAsDone() {
        if (status == TaskStatus.ACTIVE.ordinal()) {
            status = TaskStatus.DONE.ordinal();
        }
    }

    @Override
    public String toString() {
        return "Task '" + description + "', status: " + TaskStatus.values()[status];
    }

    public static Task valueFrom(ResultSet rs, int listId) throws SQLException {
        String description = rs.getString("description");
        int status = rs.getInt("status");
        int id = rs.getInt("id");

        return new Task(id, listId, description, status);

    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public int getListId() {
        return listId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
