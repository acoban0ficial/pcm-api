package dev.acobano.pcm.service;

import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IEmployeeService {
    EmployeeResponseDTO findEmployee(UUID employeeId);
    Page<EmployeeResponseDTO> listEmployees(Pageable pageable);
}
