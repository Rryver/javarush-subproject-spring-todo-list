package com.kolosov.controllers;

import com.kolosov.domain.Task;
import com.kolosov.domain.TaskStatuses;
import com.kolosov.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String index(@RequestParam("page") Optional<Integer> pageNumber, Model model) {
        Page<Task> tasksPage = taskService.findPaginated(pageNumber.orElse(1) - 1);

        model.addAttribute("tasksPage", tasksPage);
        model.addAttribute("hasNextPage", tasksPage.hasNext());

        return "index";
    }

    @GetMapping("/create")
    public String createTaskForm() {
        return "form-create";
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createTask(Task task, RedirectAttributes redirectAttributes) {
        task = taskService.save(task);

        redirectAttributes.addFlashAttribute("success", "Задача " + task.getId() + " успешно создана");
        return "redirect:/";
    }

    @ModelAttribute(name = "task")
    public Task createModel() {
        return new Task();
    }

    @ModelAttribute(name = "statuses")
    public TaskStatuses[] taskStatuses() {
        return TaskStatuses.values();
    }

    @GetMapping("/update/{id}")
    public String updateTaskForm(@PathVariable(name = "id") Integer id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);

        return "form-update";
    }
    @PostMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String updateTask(@PathVariable(name = "id") Integer id, Task task, RedirectAttributes redirectAttributes) {
        task = taskService.save(task);

        redirectAttributes.addFlashAttribute("success", "Задача " + task.getId() + " успешно обновлена");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        taskService.deleteById(id);

        redirectAttributes.addFlashAttribute("success", "Задача " + id + " успешно удалена");
        return "redirect:/";
    }
}
