package dev.acobano.pcm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO extends RepresentationModel<CustomerResponseDTO> {
    private String id;
    private String firstName;
    private String lastName;
    private String taxId;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private boolean isActive;
    private AddressResponseDTO address;
}
