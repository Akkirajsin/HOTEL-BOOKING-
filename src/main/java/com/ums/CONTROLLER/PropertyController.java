package com.ums.CONTROLLER;

import com.ums.ENTITY.Property;
import com.ums.EXCEPTION.ResourceNotFound;
import com.ums.REPOSITORY.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {


    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    @GetMapping("/{locationName}") // get all the property details IN ONCE
    public ResponseEntity<List<Property>> getPropertyListing(@RequestParam String locationName){
        List<Property> propertyList=propertyRepository. listPropertyByLocationName(locationName); //RETURN THE LIST OF THE PROPERTY
        return new ResponseEntity<>(propertyList, HttpStatus.OK);
    }


    @GetMapping("/{propertyId}") //here we have to give the property id to differnatite from other  gettingMapping
    public ResponseEntity<Property> getPropertyById(@PathVariable  long propertyId){
         //id property is found give object of it
        // on id if property is not found give the message( BY USING THE CUSTOM EXCEPTION)
       Property property= propertyRepository.findById(propertyId).orElseThrow(
              ()-> new ResourceNotFound("PROPERTY NOT FOUND WITH ID"+propertyId) //supplier  functional interface

      );
       return  new ResponseEntity<>(property,HttpStatus.OK); //

    }

    //http://localhost:8080/api/v1/property?pageSize=2&pageNo=0&sortBy=id&sortDir=asc
    @GetMapping //get the pageswise fixed no of property details
    public ResponseEntity<List<Property>> getAllProperty(
            @RequestParam(name = "pageSize",defaultValue = "5",required = false) int pageSize, // each page gave only 2 data but if mention then given 5 data
            @RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo ,// page no is start with 0
            @RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,// SORTED BASED ON THE PROPERTYNAME
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir//sorted by direaction
    ){  // GET THE PROPERTIES DETAILS BASED ON THE PAGE NO
        Pageable pageable=null;//give null due to local variable
        if (sortDir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());//if nort give give in descending order
        }
        //it Hold the page size and no as a parameter
        Page<Property> all= propertyRepository.findAll(pageable); //RETURN THE PROPERTIES DETAILS OF THE PAGE AS A PROPERTY PAGE asc and desc order
        List<Property> properties= all.getContent();//convert the page to list properties BY USING THE "getContent"
        Pageable firstPage=pageable.first();
        int pageNumber= pageable.getPageNumber(); //give that page no
        int pageCapacity= pageable.getPageSize();//give page size
        int  totalPages= all.getTotalPages(); //give total pages
        long totalElements=all.getTotalElements();//get total elements on one page
        boolean hasNext=all.hasNext(); //to check next data
        boolean hasPervious=all.hasPrevious();//to check the pervious data
        boolean last=all.isLast();//check the page is last
        boolean first=all.isFirst();//check the pages is first
        //sop statements //print the details in the console
        System.out.println(pageNumber);
        System.out.println(pageSize);
        System.out.println(totalPages);
        System.out.println(totalElements);
        System.out.println(hasNext);
        System.out.println(hasPervious);
        System.out.println(last);
        System.out.println(first);
        return new ResponseEntity<>(properties, HttpStatus.OK); //return the properties list
    }
}
