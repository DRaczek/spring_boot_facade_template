package draczek.facadetemplate.user.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto do odswiezenia tokena.
 */
@Getter
@Setter
public class RefreshTokenDto {

    @NotBlank
    String token;

    public RefreshTokenDto() {
        this.token = "";
    }

    public RefreshTokenDto(String refreshToken) {
        this.token = refreshToken;
    }
}
