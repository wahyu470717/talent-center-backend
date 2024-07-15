package lib.minio;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dto.request.TalentRequest;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lib.i18n.utility.MessageUtil;
import lib.minio.configuration.property.MinioProp;
import lib.minio.exception.MinioServiceDownloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MinioSrvc {

  private static final Long DEFAULT_EXPIRY = TimeUnit.HOURS.toSeconds(1);

  private final MinioClient minio;
  private final MinioProp prop;
  private final MessageUtil message;

  private static String bMsg(String bucket) {
    return "bucket " + bucket;
  }

  private static String bfMsg(String bucket, String filename) {
    return bMsg(bucket) + " of file " + filename;
  }

  public String getLink(String filename, Long expiry) {
    try {
      return minio.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(prop.getBucketName())
              .object(filename)
              .expiry(Math.toIntExact(expiry), TimeUnit.SECONDS)
              .build());
    } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
        | InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
        | IllegalArgumentException | IOException e) {
      log.error(message.get(prop.getGetErrorMessage(), bfMsg(prop.getBucketName(), filename)) + ": " + e.getLocalizedMessage(), e);
      throw new MinioServiceDownloadException(
          message.get(prop.getGetErrorMessage(), bfMsg(prop.getBucketName(), filename)), e);
    }
  }
  
  public String getPublicLink(String filename) {
	  return this.getLink(filename, DEFAULT_EXPIRY);
  }

  private String sanitizeForFilename(String input) {
    return input.replaceAll("[^a-zA-Z0-9]", "_");
  }

  private String getFileExtension(String filename) {
    int dotIndex = filename.lastIndexOf('.');
    return (dotIndex == -1) ? "" : filename.substring(dotIndex);
  }

  public String uploadFileToMinio(TalentRequest request, MultipartFile  talentFile) throws IOException {
        String talentName = sanitizeForFilename(request.getTalentName());

        if (talentName.isEmpty()) {
            log.warn("One or more components for filename are empty. Talent: {},",
                    request.getTalentName());
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = getFileExtension(talentFile.getOriginalFilename());

        String generatedFilename = String.format(
                "%s_%s_%s_%s%s",
                talentName,
                timestamp,
                fileExtension);

        try (InputStream inputStream = talentFile.getInputStream()) {
            minio.putObject(
                    PutObjectArgs.builder()
                            .bucket(prop.getBucketName())
                            .object(generatedFilename)
                            .stream(inputStream, talentFile.getSize(), -1)
                            .contentType(talentFile.getContentType())
                            .build());
        } catch (Exception e) {
            throw new IOException("Failed to upload file to MinIO", e);
        }

        log.info(generatedFilename);
        return generatedFilename;
    }

    public String updateFileToMinio(TalentRequest request, MultipartFile talentFile) throws IOException {
        String talentName = sanitizeForFilename(request.getTalentName());

        if (talentName.isEmpty() ) {
            log.warn("One or more components for filename are empty. Talent: {}",
                    request.getTalentName());
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = getFileExtension(talentFile.getOriginalFilename());

        String generatedFilename = String.format(
                "%s_%s_%s_%s%s",
                talentName,
                timestamp,
                fileExtension);

        try (InputStream inputStream = talentFile.getInputStream()) {
            minio.putObject(
                    PutObjectArgs.builder()
                            .bucket(prop.getBucketName())
                            .object(generatedFilename)
                            .stream(inputStream, talentFile.getSize(), -1)
                            .contentType(talentFile.getContentType())
                            .build());
        } catch (Exception e) {
            throw new IOException("Failed to upload file to MinIO", e);
        }

        log.info(generatedFilename);
        return generatedFilename;
    }
}
