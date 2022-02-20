package ru.akirakozov.sd.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.akirakozov.sd.mvc.dao.TaskDao;
import ru.akirakozov.sd.mvc.model.Task;
import ru.akirakozov.sd.mvc.model.TaskList;

import java.util.List;

/**
 * @author akirakozov
 */
@Controller
public class TaskController {

    private final TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/lists";
    }

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String showTaskLists(ModelMap map) {
        List<TaskList> lists = taskDao.getAllLists();
        lists.forEach(l -> taskDao.getTasksByListId(l.getId()).forEach(l::addTask));
        prepareModelMap(map, lists);
        return "index";
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addList(@ModelAttribute("taskList") TaskList taskList) {
        taskDao.addList(taskList);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task) {
        taskDao.addTask(task);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/delete-list", method = RequestMethod.POST)
    public String deleteList(@RequestParam(name = "taskListId") int listId) {
        taskDao.deleteList(listId);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/mark-task", method = RequestMethod.POST)
    public String markAsDone(@RequestParam(name = "taskId") int taskId, @RequestParam(name = "listId") int listId) {
        taskDao.markAsDone(taskId, listId);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/delete-task", method = RequestMethod.POST)
    public String deleteTask(@RequestParam(name = "taskId") int taskId, @RequestParam(name = "listId") int listId) {
        taskDao.deleteTask(taskId, listId);
        return "redirect:/lists";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("taskLists", lists);
        map.addAttribute("taskList", new TaskList());
        map.addAttribute("task", new Task());
    }
}