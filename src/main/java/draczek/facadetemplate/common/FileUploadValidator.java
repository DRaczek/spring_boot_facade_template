package draczek.facadetemplate.common;

import draczek.facadetemplate.common.dto.ViolationDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * File uploads validation helper class.
 */
@RequiredArgsConstructor
public class FileUploadValidator {
  //5MB
  private final long maxFileSizeInBytes = 5242880;
  private final String[] supportedContentTypes = {
    "image/png",
    "image/jpg",
    "image/jpeg",
  };

  /**
   * Method for MultipartFile validation.
   *
   * @param multipartFile MultipartFile
   * @return list of violations
   */
  public List<ViolationDto> validate(MultipartFile multipartFile) {
    List<ViolationDto> violations = new ArrayList<>();

    ViolationDto violationDto = validateFileSize(multipartFile);
    if (violationDto != null) {
      violations.add(violationDto);
    }

    violationDto = validateFileType(multipartFile);
    if (violationDto != null) {
      violations.add(violationDto);
    }

    return violations;
  }

  private ViolationDto validateFileSize(MultipartFile multipartFile) {
    if (multipartFile.getSize() > maxFileSizeInBytes) {
      return new ViolationDto(
          "too_big_file",
          "fileIsTooBig",
          "Max file size is 5MB");
    }
    return null;
  }

  private ViolationDto validateFileType(MultipartFile multipartFile) {
    if (!Arrays.asList(supportedContentTypes).contains(multipartFile.getContentType())) {
      return new ViolationDto(
          "wrong_file_type",
          "wrongFileType",
          "Supported content types are " + String.join(", ", supportedContentTypes));
    }
    return null;
  }
}
