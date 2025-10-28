package draczek.facadetemplate.common;

import draczek.facadetemplate.configuration.FileStorageProperties;
import draczek.facadetemplate.user.domain.command.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * File storage service class.
 */
@Service
@Slf4j
public class FileStorageService {
  private final Path fileStorageLocation;
  private final Path userProfilePictureStorageLocation;
  private final FileStorageProperties fileStorageProperties;

  /**
   * FileStorageService constructor.
   *
   * @param fileStorageProperties File Storage properties
   */
  @Autowired
  public FileStorageService(FileStorageProperties fileStorageProperties) {
    this.fileStorageProperties = fileStorageProperties;
    this.fileStorageLocation
        = Paths.get(fileStorageProperties.getFullUploadDir()).toAbsolutePath().normalize();
    this.userProfilePictureStorageLocation
        = Paths.get(fileStorageProperties.getFullUserProfilePictureUploadDir())
        .toAbsolutePath()
        .normalize();
    try {
      Files.createDirectories(this.fileStorageLocation);
      Files.createDirectories(this.userProfilePictureStorageLocation);
    } catch (Exception ex) {
      throw new RuntimeException("Unexpected Error", ex);
    }
  }

  /**
   * Method for saving images.
   *
   * @param file      MultipartFile
   * @param classType class
   * @param entityId  entity id
   * @return relative path
   */
  public String storeFile(MultipartFile file, Class<?> classType, int entityId) {
    String relativePath = fileStorageProperties.getUploadDir();
    Path storageLocation = this.fileStorageLocation;
    if (classType == User.class) {
      storageLocation = this.userProfilePictureStorageLocation;
      relativePath = fileStorageProperties.getUserProfilePictureUploadDir();
    }

    String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
        .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

    String newFileName = String.format("%d.%s", entityId, fileExtension);

    String fileName = StringUtils.cleanPath(newFileName);
    try {
      Path targetLocation = storageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
    return relativePath + "/" + fileName;
  }

  /**
   * Method for deleting upload files.
   *
   * @param imgSource Image source
   */
  public void deleteFileIfExists(String imgSource) throws IOException {
    imgSource = fileStorageProperties.getUploadDirPrefix() + imgSource;
    Path imgPath = Paths.get(imgSource);
    if (Files.exists(imgPath)) {
      Files.deleteIfExists(imgPath);
    }
  }
}
