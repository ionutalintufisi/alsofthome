package alsofthome.be.service;

import alsofthome.be.entities.Inscriere;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void trimiteEmail(Inscriere inscriere) {

        try {

            // 1️⃣ Cream Excel-ul
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Inscriere");

            Row header = sheet.createRow(0);
            Row data = sheet.createRow(1);

            int col = 0;

            header.createCell(col).setCellValue("Elev");
            data.createCell(col++).setCellValue(inscriere.numeElev);

            header.createCell(col).setCellValue("Curs");
            data.createCell(col++).setCellValue(inscriere.curs);

            header.createCell(col).setCellValue("Clasa");
            data.createCell(col++).setCellValue(inscriere.clasa);

            header.createCell(col).setCellValue("Tip plata");
            data.createCell(col++).setCellValue(inscriere.tipPlata);

            header.createCell(col).setCellValue("Parinte");
            data.createCell(col++).setCellValue(inscriere.numePar);

            header.createCell(col).setCellValue("Telefon");
            data.createCell(col++).setCellValue(inscriere.telefon);

            header.createCell(col).setCellValue("Email");
            data.createCell(col++).setCellValue(inscriere.email);

            header.createCell(col).setCellValue("Metoda plata");
            data.createCell(col++).setCellValue(inscriere.metodaPlata);

            header.createCell(col).setCellValue("Mesaj");
            data.createCell(col++).setCellValue(inscriere.mesaj);

            if ("online".equals(inscriere.metodaPlata)) {

                header.createCell(col).setCellValue("Card Nume");
                data.createCell(col++).setCellValue(inscriere.cardNume);

                header.createCell(col).setCellValue("Card Nr");
                data.createCell(col++).setCellValue(inscriere.cardNr);

                header.createCell(col).setCellValue("Card Exp");
                data.createCell(col++).setCellValue(inscriere.cardExp);

                header.createCell(col).setCellValue("Card CVV");
                data.createCell(col++).setCellValue(inscriere.cardCvv);
            }

            header.createCell(col).setCellValue("Acord Date");
            data.createCell(col++).setCellValue(inscriere.acordDate);

            header.createCell(col).setCellValue("Acord Comunicari");
            data.createCell(col++).setCellValue(inscriere.acordComunicari);

            // auto-size coloane
            for (int i = 0; i < col; i++) {
                sheet.autoSizeColumn(i);
            }

            // 2️⃣ Convertim Excel-ul in bytes
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();

            // 3️⃣ Trimitem email cu atasament
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("info@alsofthome.ro");
            helper.setSubject("Noua inscriere la curs: " + inscriere.curs);
            helper.setText("Gasiti atasat formularul de inscriere.");

            helper.addAttachment(
                    "inscriere.xlsx",
                    () -> new java.io.ByteArrayInputStream(bos.toByteArray())
            );

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}