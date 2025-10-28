package draczek.facadetemplate.user.domain.command;

import draczek.facadetemplate.common.FileUploadValidator;
import draczek.facadetemplate.common.dto.ViolationDto;
import draczek.facadetemplate.user.domain.exception.UpdateUserInvalidException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * User's entity update validation helper class.
 */
@RequiredArgsConstructor
public class UpdateUserValidationHelper {
  private final FileUploadValidator fileUploadValidator;

  /**
   * Method for UpdateAccountDto validation.
   *
   * @param multipartFile MultipartFile
   */
  public void validate(MultipartFile multipartFile) {
    List<ViolationDto> violations = new ArrayList<>(
        fileUploadValidator.validate(multipartFile)
    );

    if (!violations.isEmpty()) {
      throw new UpdateUserInvalidException(multipartFile, violations);
    }
  }
}
