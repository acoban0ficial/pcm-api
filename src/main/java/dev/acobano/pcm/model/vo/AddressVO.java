package dev.acobano.pcm.model.vo;

import dev.acobano.pcm.model.enumerated.Country;
import dev.acobano.pcm.model.enumerated.StreetType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AddressVO {

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "street_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StreetType streetType;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false, length = 5)
    private String zipCode;

    @Column(name = "state", nullable = false)
    private String state;

    @Enumerated(EnumType.STRING)
    private Country country;
}
