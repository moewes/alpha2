package net.moewes;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailMessage;
import net.moewes.cloudui.annotations.CloudUiView;
import net.moewes.cloudui.html.Div;
import net.moewes.cloudui.ui5.Ui5Button;

import javax.inject.Inject;

@CloudUiView("/hallo")
public class TestView extends Div {

    @Inject
    Mailer mailer;

    @Inject
    MailClient mailClient;

    public TestView() {
        Div hallo = new Div();
        hallo.setInnerHtml("Hallo");
        add(hallo);
        Ui5Button button = new Ui5Button();
        add(button);
        button.setText("Send Email");
        button.addClickHandler(uiEvent -> {
            System.out.println("before Send Mail");
         // mailer.send(Mail.withText("maik.moewes@t-online.de","Test from Quarkus","Hallo from Quarkus"));
          sendMail();
            System.out.println("Send Mail");
        });
    }

    private void sendMail() {

        MailMessage message = new MailMessage();
        message.setFrom("xxxs@outlook.de"); // FIXME
        message.setTo("xxx@t-online.de"); // FIXME
        message.setText("this is the plain message text");
       // message.setHtml("this is html text <a href=\"http://vertx.io\">vertx.io</a>");
        mailClient.sendMail(message)
                .onSuccess(System.out::println)
                .onFailure(Throwable::printStackTrace);
    }
}
