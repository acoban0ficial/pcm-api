package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.request.TeamPostRequestDTO;
import dev.acobano.pcm.dto.request.TeamPutRequestDTO;
import dev.acobano.pcm.dto.response.TeamResponseDTO;
import dev.acobano.pcm.exception.TeamNotFoundException;
import dev.acobano.pcm.mapper.ITeamMapper;
import dev.acobano.pcm.model.entity.EmployeeEntity;
import dev.acobano.pcm.model.entity.TeamEntity;
import dev.acobano.pcm.repository.EmployeeJpaRepository;
import dev.acobano.pcm.repository.TeamJpaRepository;
import dev.acobano.pcm.service.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {

    private final TeamJpaRepository teamRepository;
    private final ITeamMapper teamMapper;
    private final EmployeeJpaRepository employeeRepository;

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
                    throw new TeamNotFoundException("Employee leader not found with ID: " + dto.employeeLeaderId().toString());
                }

                entity.setLeader(leaderOpt.get());
            }

            entity.setLastUpdateDate(LocalDateTime.now());
        }
    }
}
