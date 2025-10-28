package draczek.facadetemplate.auth.command.domain;

import draczek.facadetemplate.userActionToken.domain.command.UserActionToken;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * Class for stuff related to sending mails.
 */
@RequiredArgsConstructor
class SendMailUseCase {

  private final JavaMailSender emailSender;

  private void sendMail(String sendTo, String subject, String text) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(sendTo);
    simpleMailMessage.setFrom(((JavaMailSenderImpl) emailSender).getUsername());
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(text);
    emailSender.send(simpleMailMessage);
  }

  /**
   * Method for sending new user's activation token keys.
   *
   * @param userActionToken token
   */
  public void sendActivateAccountMail(UserActionToken userActionToken) {
    sendMail(userActionToken.getUser().getUsername(),
        "Activate your account",
        URLEncoder.encode(userActionToken.getKey(), StandardCharsets.UTF_8));
  }

  /**
   * Method for sending reset password token keys.
   *
   * @param userActionToken token
   */
  public void sendResetPasswordMail(UserActionToken userActionToken) {
    sendMail(userActionToken.getUser().getUsername(),
        "Reset your password",
        userActionToken.getKey());
  }
}
