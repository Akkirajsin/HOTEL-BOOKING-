package com.ums.CONTROLLER;

import com.ums.ENTITY.AppUser;
import com.ums.ENTITY.Booking;
import com.ums.ENTITY.Property;
import com.ums.REPOSITORY.BookingRepository;
import com.ums.REPOSITORY.PropertyRepository;

import com.ums.SERVICE.BucketService;
import com.ums.SERVICE.PDFService;
import com.ums.SERVICE.TwilioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.ion.IonException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private TwilioService twilioService;
    private PDFService pdfService;
    private BucketService bucketService;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, TwilioService twilioService, PDFService pdfService, BucketService bucketService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.twilioService = twilioService;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
    }

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createBooking(@RequestParam long propertyId, @RequestBody Booking booking, @AuthenticationPrincipal AppUser user) { //give the Booking nad Appuser for booking
        Property property = propertyRepository.findById(propertyId).get();//find the propertyid and get it
        int nightlyPrice = property.getNightlyPrice(); //nightly price of the property
        int totalPrice = nightlyPrice * booking.getTotalNights(); //calculate the totel price
        // double priceWithTax= totalPrice*(18/100); //taxex per night
        booking.setTotalPrice(totalPrice);
        booking.setProperty(property);
        booking.setAppUser(user);
        Booking saveBooking = bookingRepository.save(booking); //to save the booking
        String filePath=pdfService.generateBookingDetailsPdf(saveBooking); //take trhe path  file from the PDFService file
        try {
         MultipartFile fileMultipart =  BookingController.convert(filePath);
         String fileUploadedUrl=  bucketService.uploadFile( fileMultipart,"ankitrajsingh0905");
            System.out.println(fileUploadedUrl);
            sendMessage(fileUploadedUrl);
        }catch (IonException | IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(saveBooking, HttpStatus.CREATED);
    }

    @PostMapping("/sendSms")
    public void sendMessage(String url) { //this call the senSMS method in the smsServicelayer
        twilioService.sendSms("+919009235458", "your booking is confirmed.Click here"+url);
    }

    public static MultipartFile convert(String filePath) throws IOException {
        File file = new File(filePath);

        // Validate if the file exists
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Create a MultipartFile object using a ByteArrayResource
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                // Determine content type based on file extension or use a default value
                return "application/octet-stream";
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return file.length();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return Files.newInputStream(file.toPath());
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), fileContent);
            }
        };

        return multipartFile;
    }

}







