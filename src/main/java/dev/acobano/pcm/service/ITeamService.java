package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.request.TeamPostRequestDTO;
import dev.acobano.pcm.dto.request.TeamPutRequestDTO;
import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import dev.acobano.pcm.dto.response.TeamResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITeamService {
    TeamResponseDTO findTeam(UUID teamId);
    Page<TeamResponseDTO> listTeams(Pageable pageable);
    EmployeeResponseDTO getTeamLeader(UUID teamId);
    Page<EmployeeResponseDTO> getTeamMembers(UUID teamId, Pageable pageable);
    TeamResponseDTO saveTeam(TeamPostRequestDTO input);
    void addTeamMember(UUID teamId, UUID employeeId);
    TeamResponseDTO updateTeam(UUID teamId, TeamPutRequestDTO input);
    void logicalDeleteTeam(UUID teamId);
    void removeTeamMember(UUID teamId, UUID employeeId);
}
