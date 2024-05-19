package com.ukma.mail.service;

import com.ukma.mail.dto.EmailBasketItemDto;
import com.ukma.mail.dto.MailDto;
import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MailService {

  private MailClient mailClient;


  public void sendEmail(MailDto mailDto) {
    MailMessage message = new MailMessage();
    message.setFrom("chotkiypaca349@gmail.com");
    message.setTo(mailDto.getReceiver());
    message.setSubject("Notification from vert.x email service");
    message.setHtml(createHtmlContent(mailDto.getUsername(), mailDto.getBasketItems()));

    mailClient.sendMail(message, result -> {
      if (result.succeeded()) {
        System.out.println(result.result());
      } else {
        result.cause().printStackTrace();
      }
    });
  }

  public MailService(Vertx vertx) {
    MailConfig config = new MailConfig();
    config.setHostname("smtp.gmail.com");
    config.setPort(587);
    config.setPassword("rhcz ftgo doyg tiuf");
    config.setUsername("chotkiypaca349@gmail.com");
    config.setStarttls(StartTLSOptions.REQUIRED);
    this.mailClient = MailClient.create(vertx, config);
  }


  public String createHtmlContent(String receiverName, List<EmailBasketItemDto> basketItems) {
    String html = """
       <p>Dear <b>%s</b>! Thank you for your order! Soon you will get following shoes:</p>
       <table >
          <thead>
          <tr>
              <th width="80">Image</th>
              <th width="80">Name</th>
              <th width="80">Amount</th>
              <th width="80">Size</th>
              <th width="80">Unit Price</th>
              <th width="80">Total price</th>
          </tr>
          </thead>
          <tbody>
      """.formatted(receiverName);
    int sum = 0;

    for (EmailBasketItemDto item : basketItems) {
      int currSum = item.getPrice() * item.getAmount();
      html += """
              <tr>
              <td>
              <img
              width="80"
              height="80"
              src="%s"
              style="cursor: pointer"
          />
          </td>
          <td style="text-align: center" width="80">%s</td>
          <td style="text-align: center" width="80">%d</td>
          <td style="text-align: center" width="80">%.1f</td>
          <td style="text-align: center" width="80">%d hrn</td>
          <td style="text-align: center" width="80"<b>%d hrn</b></td>
          </tr>
        """.formatted(
        item.getImage(),
        item.getName(),
        item.getAmount(),
        item.getSize(),
        item.getPrice(),
        currSum
      );
      sum += currSum;
    }

    html += """
            <tr>
            <td colspan="5"></td>
          <td style="text-align: center" width="100"><b>%d hrn</b></td>
            </tr>
            </tbody>
            </table>
      """.formatted(sum);

    return html;
  }
}
