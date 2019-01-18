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

    public void sendPdfFile(String emailAddress, OrderDTO orderDTO) throws FileNotFoundException, DocumentException, MessagingException
    {
        String filename = orderDTO.getNumber() + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        prepareInvoiceContent(document, font, orderDTO);
        document.close();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailAddress);
        helper.setSubject(("Invoice " + orderDTO.getNumber()));
        helper.setText(String.format(
                "Hello! Invoice for your order with number: %s can be found in email's attachments",
                orderDTO.getNumber())
        );
        helper.addAttachment(filename, new File(filename));

        mailSender.send(message);
    }

    private void prepareInvoiceContent(Document document, Font font, OrderDTO orderDTO) throws DocumentException
    {
        document.add(new Paragraph("Invoice number " + orderDTO.getNumber(), font));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph("Line item: ", font));
        document.add(new Paragraph(""));
        document.add(new Paragraph(orderDTO.getProductName(), font));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph("Total price: " + orderDTO.getTotal(), font));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph("Thank you for shopping with us!", font));
    }
}
