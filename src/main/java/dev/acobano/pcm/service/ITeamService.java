package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.response.TeamResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITeamService {
    TeamResponseDTO findTeam(UUID teamId);
    Page<TeamResponseDTO> listTeams(Pageable pageable);
}
