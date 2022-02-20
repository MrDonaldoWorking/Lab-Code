package ru.akirakozov.sd.mvc.dao;

import ru.akirakozov.sd.mvc.model.Task;
import ru.akirakozov.sd.mvc.model.TaskList;

import java.util.List;

/**
 * @author akirakozov
 */
public interface TaskDao {
    List<TaskList> getAllLists();

    List<Task> getTasksByListId(int listId);

    void addList(TaskList list);

    void deleteList(int listId);

    void addTask(Task task);

    void deleteTask(int taskId, int listId);

    void markAsDone(int taskId, int listId);
}
