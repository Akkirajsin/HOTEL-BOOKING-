package com.ums.CONTROLLER;


import com.ums.SERVICE.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("s3bucket")
@CrossOrigin("*") //by using this we can access this api in angular as well
public class BucketController {
    @Autowired
    BucketService service; //add the  Bucketservice layer  dependcies
    @PostMapping(path="/upload/file/{bucketName}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces =MediaType.APPLICATION_GRAPHQL_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file, @PathVariable String bucketName){
        return new ResponseEntity<>(service.uploadFile(file,bucketName), HttpStatus.OK); //take the file and bucketName and give to the service layer and return url of the file
    }
}
