package com.kolosov.services;

import com.kolosov.dao.TaskDao;
import com.kolosov.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskDao taskRepository;

    private final int pageSize = 5;

    public TaskService(TaskDao taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> findPaginated(int pageNumber) {
        return taskRepository.findAll(getPageable(pageNumber));
    }

    public Pageable getPageable(int pageNumber) {
        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task findById(Integer id) {
        return taskRepository.findById(id).orElseThrow();
    }
}
