package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.request.ProjectPostRequestDTO;
import dev.acobano.pcm.dto.request.ProjectPutRequestDTO;
import dev.acobano.pcm.dto.response.ProjectResponseDTO;
import dev.acobano.pcm.exception.CustomerNotFoundException;
import dev.acobano.pcm.exception.ProjectNotFoundException;
import dev.acobano.pcm.mapper.IProjectMapper;
import dev.acobano.pcm.model.entity.ProjectEntity;
import dev.acobano.pcm.repository.CustomerJpaRepository;
import dev.acobano.pcm.repository.ProjectJpaRepository;
import dev.acobano.pcm.repository.TeamJpaRepository;
import dev.acobano.pcm.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectJpaRepository projectRepository;
    private final IProjectMapper projectMapper;
    private final CustomerJpaRepository customerRepository;
    private final TeamJpaRepository teamRepository;

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
        return projectMapper.toResponseDTO(
                projectRepository.save(
                        projectMapper.toEntity(input)
                )
        );
    }

    @Override
    public ProjectResponseDTO updateProject(UUID projectId, ProjectPutRequestDTO input) {
        Optional<ProjectEntity> projectOpt = projectRepository.findById(projectId);

        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }

        ProjectEntity project = projectOpt.get();
        updateProjectFields(input, project);
        return null;
    }

    private void updateProjectFields(ProjectPutRequestDTO dto, ProjectEntity entity) {
        if (!Objects.isNull(dto)) {
            if (!Objects.isNull(dto.name())) {
                entity.setName(dto.name());
            }

            if (!Objects.isNull(dto.description())) {
                entity.setDescription(dto.description());
            }

            if (!Objects.isNull(dto.status())) {
                entity.setStatus(dto.status());
            }

            if (!Objects.isNull(dto.startDate())) {
                entity.setStartDate(dto.startDate());
            }

            if (!Objects.isNull(dto.endDate())) {
                entity.setEndDate(dto.endDate());
            }

            if (!Objects.isNull(dto.budgetPrice())) {
                entity.getBudget().setPrice(dto.budgetPrice());
            }

            if (!Objects.isNull(dto.budgetCurrency())) {
                entity.getBudget().setCurrency(dto.budgetCurrency());
            }

            if (!Objects.isNull(dto.customerId())) {
                entity.setCustomer(
                        customerRepository.findById(dto.customerId()).get()
                );
            }

            if (!Objects.isNull(dto.assignedTeamId())) {
                entity.setTeam(
                        teamRepository.findById(dto.assignedTeamId()).get()
                );
            }
        }
    }

    @Override
    public void logicalDeleteProject(UUID projectId) {
        Optional<ProjectEntity> projectOpt = projectRepository.findById(projectId);

        if (projectOpt.isEmpty()) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }

        ProjectEntity project = projectOpt.get();
        project.setActive(Boolean.FALSE);
        projectRepository.save(project);
    }
}
