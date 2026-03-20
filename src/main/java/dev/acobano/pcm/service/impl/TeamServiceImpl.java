package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.response.TeamResponseDTO;
import dev.acobano.pcm.exception.TeamNotFoundException;
import dev.acobano.pcm.mapper.ITeamMapper;
import dev.acobano.pcm.model.entity.TeamEntity;
import dev.acobano.pcm.repository.TeamJpaRepository;
import dev.acobano.pcm.service.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {

    private final TeamJpaRepository teamRepository;
    private final ITeamMapper teamMapper;

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
}
