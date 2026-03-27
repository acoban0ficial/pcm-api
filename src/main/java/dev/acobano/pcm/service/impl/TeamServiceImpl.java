package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.request.TeamPostRequestDTO;
import dev.acobano.pcm.dto.request.TeamPutRequestDTO;
import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import dev.acobano.pcm.dto.response.TeamResponseDTO;
import dev.acobano.pcm.exception.EmployeeNotFoundException;
import dev.acobano.pcm.exception.TeamNotFoundException;
import dev.acobano.pcm.mapper.IEmployeeMapper;
import dev.acobano.pcm.mapper.ITeamMapper;
import dev.acobano.pcm.model.entity.EmployeeEntity;
import dev.acobano.pcm.model.entity.TeamEntity;
import dev.acobano.pcm.repository.EmployeeJpaRepository;
import dev.acobano.pcm.repository.TeamJpaRepository;
import dev.acobano.pcm.service.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {

    private final TeamJpaRepository teamRepository;
    private final ITeamMapper teamMapper;
    private final EmployeeJpaRepository employeeRepository;
    private final IEmployeeMapper employeeMapper;

    @Override
    public TeamResponseDTO findTeam(UUID teamId) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);

        if (teamOpt.isEmpty()) {
            throw new TeamNotFoundException("Team not found with ID: " + teamId.toString());
        }

        return teamMapper.toResponseDTO(teamOpt.get());
    }

    @Override
    public Page<TeamResponseDTO> listTeams(Pageable pageable) {
        Page<TeamEntity> teamsPage = teamRepository.findAll(pageable);

        if (teamsPage.isEmpty()) {
            throw new TeamNotFoundException("No teams found in the system.");
        }

        return teamsPage.map(teamMapper::toResponseDTO);
    }

    @Override
    public EmployeeResponseDTO getTeamLeader(UUID teamId) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);

        if (teamOpt.isEmpty()) {
            throw new TeamNotFoundException("Team not found with ID: " + teamId.toString());
        }

        return employeeMapper.toResponseDTO(
                teamOpt.get().getLeader()
        );
    }

    @Override
    public Page<EmployeeResponseDTO> getTeamMembers(UUID teamId, Pageable pageable) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);

        if (teamOpt.isEmpty()) {
            throw new TeamNotFoundException("Team not found with ID: " + teamId.toString());
        }

        Set<EmployeeEntity> members = teamOpt.get().getMembers();
        List<EmployeeResponseDTO> responseList = members.stream()
                .map(employeeMapper::toResponseDTO)
                .toList();

        // Calcular el rango de elementos para la página actual
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseList.size());

        // Extraer los elementos de la página actual
        List<EmployeeResponseDTO> pageContent = responseList.subList(start, end);

        // Crear y retornar el Page con los datos de paginación
        return new PageImpl<>(pageContent, pageable, responseList.size());
    }

    @Override
    public TeamResponseDTO saveTeam(TeamPostRequestDTO input) {
        TeamEntity entity = teamMapper.toEntity(input);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreationDate(now);
        entity.setLastUpdateDate(now);

        return teamMapper.toResponseDTO(
                teamRepository.save(entity)
        );
    }

    @Override
    public TeamResponseDTO updateTeam(UUID teamId, TeamPutRequestDTO input) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);

        if (teamOpt.isEmpty()) {
            throw new TeamNotFoundException("Team not found with ID: " + teamId.toString());
        }

        TeamEntity team = teamOpt.get();
        updateTeamFields(input, team);
        return teamMapper.toResponseDTO(
                teamRepository.save(team)
        );
    }

    private void updateTeamFields(TeamPutRequestDTO dto, TeamEntity entity) {

        if (!Objects.isNull(dto)) {
            if (!Objects.isNull(dto.name())) {
                entity.setName(dto.name());
            }

            if (!Objects.isNull(dto.description())) {
                entity.setDescription(dto.description());
            }

            if (!Objects.isNull(dto.employeeLeaderId())) {
                Optional<EmployeeEntity> leaderOpt = employeeRepository.findById(dto.employeeLeaderId());

                if (leaderOpt.isEmpty()) {
                    throw new EmployeeNotFoundException("Employee leader not found with ID: "
                            .concat(dto.employeeLeaderId().toString()));
                }

                entity.setLeader(leaderOpt.get());
            }

            entity.setLastUpdateDate(LocalDateTime.now());
        }
    }


    @Override
    public void logicalDeleteTeam(UUID teamId) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);

        if (teamOpt.isEmpty()) {
            throw new TeamNotFoundException("Team not found with ID: ".concat(teamId.toString()));
        }

        TeamEntity team = teamOpt.get();
        team.setActive(Boolean.FALSE);
        team.setLastUpdateDate(LocalDateTime.now());
        teamRepository.save(team);
    }
}
