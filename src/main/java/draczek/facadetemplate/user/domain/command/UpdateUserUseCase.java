package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.address.domain.command.AddressFacade;
import draczek.facadetemplate.common.FileStorageService;
import draczek.facadetemplate.common.enumerated.StatusEnum;
import draczek.facadetemplate.infrastructure.security.domain.command.SecurityFacade;
import draczek.facadetemplate.user.domain.dto.UpdateAccountDto;
import draczek.facadetemplate.user.domain.dto.UserDto;
import draczek.facadetemplate.user.domain.enumerated.SystemUserIdEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class for stuff related to updating User entity.
 */
@RequiredArgsConstructor
class UpdateUserUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SecurityFacade securityFacade;
  private final UserMapper userMapper;
  private final UpdateUserValidationHelper updateUserValidationHelper;
  private final FileStorageService fileStorageService;
  private final AddressFacade addressFacade;

  /**
   * Method for changing User's status to active.
   *
   * @param user user to activate
   */
  public void activate(User user) {
    user.setStatus(StatusEnum.ACTIVE);
    user.getAccount().setStatus(StatusEnum.ACTIVE);
    user.getAccount().getAddress().setStatus(StatusEnum.ACTIVE);
    user.setEnabled(true);
    user.setUserIdLastModified(SystemUserIdEnum.SYSTEM.getId());
    userRepository.save(user);
  }

  /**
   * Method for changing user's password after step two of password reset process.
   *
   * @param user     User to change
   * @param password new password
   */
  public void changePassword(User user, String password) {
    // no need of validation here, because password is validated before in StepTwo...UseCase
    user.setPassword(passwordEncoder.encode(password));
    user.setUserIdLastModified(user.getId());
    userRepository.saveAndFlush(user);
  }

  /**
   * Method for changing user's account data.
   *
   * @param dto UpdateAccountDto
   * @return changed UserDto
   */
  public UserDto update(UpdateAccountDto dto) {
    return updateUserAccount(dto);
  }

  /**
   * Method for changing user account's profile picture.
   *
   * @param multipartFile new profile picture
   * @return UserDto
   */
  public UserDto update(MultipartFile multipartFile) {
    updateUserValidationHelper.validate(multipartFile);
    User user = securityFacade.getLoggedInUser().getUserDetails();

    String oldPfp = user.getAccount().getProfilePictureSource();
    try {
      fileStorageService.deleteFileIfExists(oldPfp);
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error, while deleting old profile picture.");
    }

    user.getAccount().setProfilePictureSource(
        fileStorageService.storeFile(multipartFile,
            User.class,
            user.getId().intValue()));
    userRepository.saveAndFlush(user);
    return userMapper.toDto(user);
  }

  private UserDto updateUserAccount(UpdateAccountDto dto) {
    User user = securityFacade.getLoggedInUser().getUserDetails();
    user.getAccount().setFirstName(dto.getFirstName());
    user.getAccount().setLastName(dto.getLastName());
    user.getAccount().setBirthDate(dto.getBirthDate());
    user.getAccount().setPhoneNumber(dto.getPhoneNumber());
    if(dto.getUpdateAddressDto() != null) {
      user.getAccount().setAddress(addressFacade.update(dto.getUpdateAddressDto()));
    }
    user.getAccount().setVersion(dto.getVersion());
    return userMapper.toDto(userRepository.saveAndFlush(user));
  }

  /**
   * Method for logged-in user's profile picture deletion.
   *
   * @return UserDto
   */
  public UserDto deleteProfilePicture() {
    User user = securityFacade.getLoggedInUser().getUserDetails();
    try {
      fileStorageService.deleteFileIfExists(user.getAccount().getProfilePictureSource());
    } catch (Exception exception) {
      throw new RuntimeException("Unexpected error during profile picture deletion."
          + " Please contact service provider.");
    }
    user.getAccount().setProfilePictureSource(null);
    userRepository.save(user);
    return userMapper.toDto(user);
  }
}
