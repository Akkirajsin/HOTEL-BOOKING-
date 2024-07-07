package com.ums.SERVICE;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ums.ENTITY.Booking;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {
    public String generateBookingDetailsPdf(Booking booking) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D://airbnb-booking//booking-confirmation" + booking.getId() + ".pdf"));

            document.open();

            // Create font styles
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            // Create table
            PdfPTable table = new PdfPTable(2); // 2 columns
            table.setWidthPercentage(100);

            // Add table headers
            PdfPCell headerCell;
            headerCell = new PdfPCell(new Phrase("Attribute", headerFont));
            table.addCell(headerCell);
            headerCell = new PdfPCell(new Phrase("Value", headerFont));
            table.addCell(headerCell);

            // Add booking details to the table
            addRowToTable(table, "Guest Name", booking.getGuestName(), cellFont);
            addRowToTable(table, "Total Nights", String.valueOf(booking.getTotalNights()), cellFont);
            addRowToTable(table, "Total Price", String.valueOf(booking.getTotalPrice()), cellFont);
            addRowToTable(table, "Booking Date", booking.getBookingDate().toString(), cellFont);
            addRowToTable(table, "Check-in Time", String.valueOf(booking.getCheckInTime()), cellFont);
            addRowToTable(table, "Email ID", booking.getEmailId(), cellFont);

            // Add table to the document
            document.add(table);
            document.close();
            return "D://airbnb-booking//booking-confirmation" + booking.getId() + ".pdf";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void addRowToTable(PdfPTable table, String attribute, String value, Font font) {
        table.addCell(new Phrase(attribute, font));
        table.addCell(new Phrase(value, font));

    }


}

