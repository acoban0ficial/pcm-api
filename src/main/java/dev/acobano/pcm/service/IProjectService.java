package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.dto.request.ProjectPutRequestDTO;
import dev.acobano.pcm.dto.response.CustomerResponseDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProjectService {
    ProjectResponseDTO findProject(UUID projectId);
    Page<ProjectResponseDTO> listProjects(Pageable pageable);
    CustomerResponseDTO getProjectCustomer(UUID projectId);
    ProjectResponseDTO saveProject(ProjectPostRequestDTO input);
    ProjectResponseDTO updateProject(UUID projectId, ProjectPutRequestDTO input);
    void logicalDeleteProject(UUID projectId);
}
