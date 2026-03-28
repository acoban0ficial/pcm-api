package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.response.TaskResponseDTO;
import dev.acobano.pcm.exception.TaskNotFoundException;
import dev.acobano.pcm.mapper.ITaskMapper;
import dev.acobano.pcm.model.entity.TaskEntity;
import dev.acobano.pcm.repository.TaskJpaRepository;
import dev.acobano.pcm.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskJpaRepository taskRepository;
    private final ITaskMapper taskMapper;

    @Override
    public TaskResponseDTO findTask(UUID taskId) {
        Optional<TaskEntity> taskOpt = taskRepository.findById(taskId);

        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task not found with ID: " + taskId.toString());
        }

        return taskMapper.toResponseDTO(taskOpt.get());
    }

    @Override
    public Page<TaskResponseDTO> listTasks(Pageable pageable) {
        Page<TaskEntity> tasksPage = taskRepository.findAll(pageable);

        if (tasksPage.isEmpty()) {
            throw new TaskNotFoundException("No tasks found in the system.");
        }

        return tasksPage.map(taskMapper::toResponseDTO);
    }
}
