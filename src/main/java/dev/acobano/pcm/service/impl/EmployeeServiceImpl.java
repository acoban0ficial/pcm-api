package dev.acobano.pcm.service.impl;

import dev.acobano.pcm.dto.response.EmployeeResponseDTO;
import dev.acobano.pcm.exception.EmployeeNotFoundException;
import dev.acobano.pcm.mapper.IEmployeeMapper;
import dev.acobano.pcm.model.entity.EmployeeEntity;
import dev.acobano.pcm.repository.EmployeeJpaRepository;
import dev.acobano.pcm.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeJpaRepository employeeRepository;
    private final IEmployeeMapper employeeMapper;


    @Override
    public EmployeeResponseDTO findEmployee(UUID employeeId) {
        Optional<EmployeeEntity> employeeOpt = employeeRepository.findById(employeeId);

        if (employeeOpt.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found with ID: ".concat(employeeId.toString()));
        }

        return employeeMapper.toResponseDTO(employeeOpt.get());
    }

    @Override
    public Page<EmployeeResponseDTO> listEmployees(Pageable pageable) {
        Page<EmployeeEntity> employeesPage = employeeRepository.findAll(pageable);

        if (employeesPage.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found in the system.");
        }

        return employeesPage.map(employeeMapper::toResponseDTO);
    }
}
