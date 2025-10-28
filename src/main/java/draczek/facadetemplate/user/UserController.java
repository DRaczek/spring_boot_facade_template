package draczek.facadetemplate.user;

import draczek.facadetemplate.user.domain.command.UserFacade;
import draczek.facadetemplate.user.domain.dto.UpdateAccountDto;
import draczek.facadetemplate.user.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * User's package controller.
 */
@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserFacade userFacade;

  @GetMapping("/info")
  @Operation(summary = "Fetch logged in user data")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public UserDto info() {
    return userFacade.info();
  }

  @PutMapping(value = "/account")
  @Operation(summary = "Change user data")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public UserDto update(@RequestBody UpdateAccountDto updateAccountDto) {
    return userFacade.update(updateAccountDto);
  }

  @PutMapping(value = "/account/pfp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Change user's profile picture")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public UserDto updateProfilePicture(@Schema(type = "string", format = "binary")
                                      @RequestPart(name = "pfp") MultipartFile multipartFile) {
    return userFacade.update(multipartFile);
  }

  @DeleteMapping(value = "/account/pfp")
  @Operation(summary = "Delete user's profile picture")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public UserDto deleteProfilePicture() {
    return userFacade.deleteProfilePicture();
  }
}
