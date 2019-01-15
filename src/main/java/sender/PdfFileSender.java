package main.java.sender;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import main.java.model.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
public class PdfFileSender
{
    private JavaMailSender mailSender;

    @Autowired
    public PdfFileSender(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void sendPdfFile(String emailAddress, OrderDTO orderDTO) throws FileNotFoundException, DocumentException
    {
        String filename = orderDTO.getNumber() + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();

        MimeMessage message = mailSender.createMimeMessage();
        try
        {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailAddress);
            helper.setSubject(("Invoice " + orderDTO.getNumber()));
            helper.setText(String.format(
                    "Hello! Invoice for your order with number: %s can be found in email's attachments",
                    orderDTO.getNumber())
            );
            helper.addAttachment(filename, new File(filename));
        }
        catch (MessagingException e)
        {
            return;
        }

        mailSender.send(message);
    }
}
