package net.moewes.berufsinfo.referenten;

import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailMessage;
import org.apache.olingo.server.api.ODataApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RegisterService {

    @ConfigProperty(name = "sender")
    String sender_email;

    @ConfigProperty(name = "serviceurl")
    String service_url;

    @Inject
    MailClient mailClient;


    public void sendMail(OdataReferent referent)
            throws ODataApplicationException {

        // TODO escape?!!! // FIXME

        String link = service_url +
                "/referent?token=" + referent.getId();

        String messageText = "Guten Tag " + referent.getName()
                + ",<br/><br/> vielen Dank für Ihre Unterstützung bei der Berufsinformation!" +
                "<br/><br/>" +
                "<a href=\"" + link + "\">Hier können Sie die Anmeldungen zu den von Ihnen " +
                "angebotenen Veranstaltungen einsehen</a>. Bitte senden Sie den angemeldeten " +
                "Schülern die" +
                " Zugangsdaten für Ihre Online Veranstaltung per Email zu." +
                " <br/> Sollten Sie Unterstützung benötigen, kontaktieren Sie mich bitte gern per" +
                " Email (maik.moewes@t-online.de)." +
                "<br/><br/>" +
                "Viele Grüße<br/>Maik Möwes";

        MailMessage message = new MailMessage();
        message.setFrom(sender_email);
        message.setTo(referent.getEmail());
        message.setBcc("maik.moewes@t-online.de");
        message.setSubject("Berufsinformation 2022");
        //message.setText("this is the plain message text");
        message.setHtml(messageText);
        mailClient.sendMail(message)
                .onSuccess(System.out::println)
                .onFailure(Throwable::printStackTrace);
    }
}
