package dev.acobano.pcm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_employees")
@Getter
@Setter
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "encrypted_password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "is_enabled")
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "tbl_employees_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "leader")
    private Set<TeamEntity> leadingTeams;

    @ManyToMany(mappedBy = "members")
    private Set<TeamEntity> memberingTeams;

    @OneToMany(mappedBy = "assignee")
    private Set<TaskEntity> tasks;
}
