package ac.at.fhcampuswien.carrental.rest.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
public class RegistrationRequestDto {
    @NotBlank
    @NotEmpty
    private String eMail;
    @NotBlank
    @NotEmpty
    private String password;
    @NotBlank
    @NotEmpty
    private String firstName;
    @NotBlank
    @NotEmpty
    private String lastName;
    @NotBlank
    @NotEmpty
    private String phoneNumber;
    @NotBlank
    @NotEmpty
    private String dateOfBirth;
}
