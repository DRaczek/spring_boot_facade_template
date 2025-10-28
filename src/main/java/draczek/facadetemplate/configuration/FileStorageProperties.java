package draczek.facadetemplate.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * File storage properties.
 */
@Component
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {
  private String uploadDirPrefix;
  private String uploadDir;
  private String userProfilePicturePostfix;

  public String getFullUploadDir() {
    return uploadDirPrefix + uploadDir;
  }

  public String getFullUserProfilePictureUploadDir() {
    return this.uploadDirPrefix + getUserProfilePictureUploadDir();
  }

  public String getUserProfilePictureUploadDir() {
    return uploadDir + userProfilePicturePostfix;
  }
}
