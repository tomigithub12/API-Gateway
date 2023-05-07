package ac.at.fhcampuswien.carrental.rest.models;

import lombok.*;

@Data
@Builder
public class RegistrationResponseDto {
    private Long id;
    private String eMail;
}
