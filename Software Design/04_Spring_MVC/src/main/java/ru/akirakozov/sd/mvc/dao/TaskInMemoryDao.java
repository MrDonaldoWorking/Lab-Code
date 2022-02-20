package ru.akirakozov.sd.mvc.dao;

import ru.akirakozov.sd.mvc.model.Task;
import ru.akirakozov.sd.mvc.model.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author akirakozov
 */
public class TaskInMemoryDao implements TaskDao {
    private final AtomicInteger lastListId = new AtomicInteger(0);
    private final AtomicInteger lastTaskId = new AtomicInteger(0);
    private final HashMap<Integer, TaskList> taskLists = new HashMap<>();
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<TaskList> getAllLists() {
        return new ArrayList<>(taskLists.values());
    }

    @Override
    public List<Task> getTasksByListId(int listId) {
        return taskLists.get(listId).getTasks();
    }

    @Override
    public void addList(TaskList list) {
        int id = lastListId.incrementAndGet();
        list.setId(id);
        taskLists.put(list.getId(), list);
    }

    @Override
    public void deleteList(int listId) {
        taskLists.remove(listId);
    }

    @Override
    public void addTask(Task task) {
        int id = lastTaskId.incrementAndGet();
        task.setTaskId(id);
        tasks.add(task);
        TaskList list = taskLists.remove(task.getListId());
        list.addTask(task);
        taskLists.put(list.getId(), list);
    }

    private Task findTask(final int taskId, final int listId) {
        for (final Task task : tasks) {
            if (task.getTaskId() == taskId && task.getListId() == listId) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void deleteTask(int taskId, int listId) {
        final Task task = findTask(taskId, listId);
        tasks.remove(task);
    }

    @Override
    public void markAsDone(int taskId, int listId) {
        final Task task = findTask(taskId, listId);
        task.markAsDone();
    }
}