package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.exception.CustomerNotFoundException;
import dev.acobano.pcm.exception.ProjectNotFoundException;
import dev.acobano.pcm.mapper.IProjectMapper;
import dev.acobano.pcm.model.entity.ProjectEntity;
import dev.acobano.pcm.repository.ProjectJpaRepository;
import dev.acobano.pcm.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectJpaRepository projectRepository;
    private final IProjectMapper projectMapper;

    @Override
    public ProjectResponseDTO findProject(UUID projectId) {
        Optional<ProjectEntity> projectOpt = projectRepository.findById(projectId);

        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }

        return projectMapper.toResponseDTO(projectOpt.get());
    }

    @Override
    public Page<ProjectResponseDTO> listProjects(Pageable pageable) {
        Page<ProjectEntity> projectsPage = projectRepository.findAll(pageable);

        if (projectsPage.isEmpty()) {
            throw new ProjectNotFoundException("No projects found in the system.");
        }

        return projectsPage.map(projectMapper::toResponseDTO);
    }

    @Override
    public ProjectResponseDTO saveProject(ProjectPostRequestDTO input) {
        return null;
    }
}
