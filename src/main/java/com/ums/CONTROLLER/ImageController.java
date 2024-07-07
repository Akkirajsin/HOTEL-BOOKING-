package com.ums.CONTROLLER;

import com.ums.ENTITY.Image;
import com.ums.ENTITY.Property;
import com.ums.REPOSITORY.ImageRepository;
import com.ums.REPOSITORY.PropertyRepository;
import com.ums.SERVICE.BucketService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private ImageRepository imageRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;  //it have the method to upload the image

    public ImageController(ImageRepository imageRepository, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imageRepository = imageRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }
    @PostMapping("/addImage")
    public ResponseEntity<Image> addImages(@RequestParam long propertyId, @RequestParam String bucketName, MultipartFile file){
      String imageUrl= bucketService.uploadFile(file,bucketName); //by using upload file in build method in bucketname
       Optional<Property> byId= propertyRepository.findById(propertyId);
     Property property= byId.get();
     Image image=new Image();
     image.setImageUrl(imageUrl);
     image.setProperty(property);
      Image saveImage=imageRepository.save(image); //to save in the data
       return new ResponseEntity<>(saveImage, HttpStatus.CREATED);
    }
    @GetMapping("/propertyImages")
    public ResponseEntity<List<Image>>fetchPropertyImages(@RequestParam long propertyId){
        List<Image> images= imageRepository.findByPropertyId(propertyId);
        return new ResponseEntity<>(images,HttpStatus.OK);

    }
}
