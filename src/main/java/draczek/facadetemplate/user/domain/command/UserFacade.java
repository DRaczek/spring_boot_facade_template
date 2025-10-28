package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.auth.command.dto.RegistrationDto;
import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.user.domain.dto.UpdateAccountDto;
import draczek.facadetemplate.user.domain.dto.UserDto;
import java.util.List;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 * User's package facade.
 */
@Validated
@RequiredArgsConstructor
@Transactional
public class UserFacade {

  private final SearchUserUseCase searchUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final UserValidationHelper userValidationHelper;
  private final UpdateUserUseCase updateUserUseCase;

  /**
   * Method for getting information about logged-in user.
   *
   * @return UserDto
   */
  public UserDto info() {
    return searchUserUseCase.info();
  }

  /**
   * Method for creating new user.
   *
   * @param registrationDto RegistrationDto
   * @return created User instance.
   */
  public User create(@NotNull RegistrationDto registrationDto) {
    return createUserUseCase.create(registrationDto);
  }

  /**
   * Helper service for user validation.
   *
   * @param username user's username
   * @return violations
   */
  public List<ViolationDto> validate(@NotBlank @Email String username) {
    return userValidationHelper.validate(username);
  }

  /**
   * Method for changing User's status to active.
   *
   * @param user user to activate
   */
  public void activate(@NotNull User user) {
    updateUserUseCase.activate(user);
  }

  /**
   * Method for getting User instance by its email and status.
   *
   * @param email  user's email
   * @param status status
   * @return User instance
   */
  public User get(@NotBlank String email, @NotNull StatusEnum status) {
    return searchUserUseCase.get(email, status);
  }

  /**
   * Method for changing user's password.
   *
   * @param user     User to change
   * @param password new password
   */
  public void changePassword(@NotNull User user, @NotBlank String password) {
    updateUserUseCase.changePassword(user, password);
  }

  /**
   * Method for changing user's account data.
   *
   * @param dto           UpdateAccountDto
   * @return UserDto
   */
  public UserDto update(@NotNull UpdateAccountDto dto) {
    return updateUserUseCase.update(dto);
  }

  public UserDto update(@NotNull MultipartFile multipartFile) {
    return updateUserUseCase.update(multipartFile);
  }

  public UserDto deleteProfilePicture() {
    return updateUserUseCase.deleteProfilePicture();
  }
}
