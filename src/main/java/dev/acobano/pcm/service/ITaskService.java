package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.response.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITaskService {
    TaskResponseDTO findTask(UUID taskId);
    Page<TaskResponseDTO> listTasks(Pageable pageable);
}
