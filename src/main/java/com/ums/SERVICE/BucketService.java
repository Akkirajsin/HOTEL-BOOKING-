package com.ums.SERVICE;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class BucketService {
    private AmazonS3 amazonS3; //add the Amazons3 dependcies by using the constructors


    public BucketService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile file, String bucketName) { //BUCKETS ARE LIKE THE FOLDER IN ONLINE CLOUD STORAGE
        if (file.isEmpty()) {
            throw new IllegalStateException("CAN NOT UPLOAD EMPTY FILE"); //CHECK THE FILE IF FILE IS  EMPTY THEN LEAVE THIS MESSAGE
        }
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile); // after upload the file
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString(); //take the  bucket name, url name and return to the url of the file
            } catch (AmazonS3Exception s3Exception) {
                return "unable to upload file:" + s3Exception.getMessage();

            }
        } catch (Exception e) {
            throw new IllegalStateException("failed to upload the file", e);
        }
    }
}

