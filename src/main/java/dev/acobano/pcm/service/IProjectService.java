package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProjectService {
    ProjectResponseDTO findProject(UUID projectId);
    Page<ProjectResponseDTO> listProjects(Pageable pageable);

}
