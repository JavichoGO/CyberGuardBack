package com.questions.questions.mail;

import com.questions.questions.dao.Users;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SendMail {

    @Autowired
    private Session mailSession;

    public static final String TEMPLATE_EMAIL="<!DOCTYPE html><html lang=\"es\"><head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Recuperación de Contraseña</title> <style> body { font-family: Arial, sans-serif; background-color: #f4f4f7; margin: 0; padding: 0; color: #51545e; } .email-container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16), 0 2px 3px rgba(0, 0, 0, 0.23); } .email-header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #dcdcdc; } .email-header h1 { margin: 0; font-size: 24px; color: #333333; } .email-body { padding: 20px 0; text-align: center; } .email-body h2 { font-size: 22px; color: #333333; margin: 0 0 20px; } .email-body p { margin: 0 0 20px; font-size: 16px; line-height: 1.5; } .email-body .token { display: inline-block; padding: 10px 20px; font-size: 18px; background-color: #3869d4; color: #ffffff; border-radius: 5px; text-decoration: none; font-weight: bold; letter-spacing: 1.2px; } .email-footer { text-align: center; padding-top: 20px; border-top: 1px solid #dcdcdc; font-size: 14px; color: #999999; } .email-footer a { color: #3869d4; text-decoration: none; } </style></head><body> <div class=\"email-container\"> <div class=\"email-header\"> <h1>Recuperación de Contraseña</h1> </div> <div class=\"email-body\"> <h2>Hola, ||name||</h2> <p>Hemos recibido una solicitud para restablecer tu contraseña. Usa el siguiente token para proceder:</p> <p class=\"token\"> <a href=\"https://cyberguardd.netlify.app/reset-password/||identification||/||token||\" target=\"_blank\">Restablecer Contraseña</a></p> <p>Si no solicitaste este cambio, puedes ignorar este correo electrónico.</p> </div> <div class=\"email-footer\"> <p>Gracias por confiar en nosotros.</p> <p>Si tienes alguna duda, puedes <a href=\"mailto:support@example.com\">contactar a nuestro soporte</a>.</p> </div> </div></body></html>";

    public void sendMail(Users users, Integer token) throws MessagingException {
        String to = users.getEmail(); // Dirección de correo electrónico del destinatario
        String from = "clever.rivera07@gmail.com"; // Dirección de correo electrónico del remitente

        //Datos de la plantilla
        Map<String, String> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name", users.getNameAll());
        stringObjectMap.put("token", token.toString());
        stringObjectMap.put("identification", users.getIdentification());
        String processedTemplate =this.processTemplate(stringObjectMap);

        // Creación de mensaje
        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Recuperación de contraseña");
        message.setContent(processedTemplate, "text/html; charset=utf-8");
        Transport.send(message);
    }

    private String processTemplate(Map<String, String> values){
        String processedTemplate = SendMail.TEMPLATE_EMAIL;

        for (Map.Entry<String, String> entry : values.entrySet()) {
            processedTemplate = processedTemplate.replace("||" + entry.getKey() + "||", entry.getValue());
        }
        return processedTemplate;
    }


}
