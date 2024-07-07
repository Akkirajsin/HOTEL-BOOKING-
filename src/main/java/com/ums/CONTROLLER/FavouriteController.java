package com.ums.CONTROLLER;

import com.ums.ENTITY.AppUser;
import com.ums.ENTITY.Favourite;
import com.ums.ENTITY.Property;
import com.ums.REPOSITORY.FavouriteRepository;
import com.ums.REPOSITORY.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

    private FavouriteRepository favouriteRepository;
    private PropertyRepository propertyRepository;


    public FavouriteController(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }
    @PostMapping("/addFav")
    public ResponseEntity<Favourite>  addFavourite(@AuthenticationPrincipal AppUser user, @RequestParam long propertyId){  //based on the propertyid
        Optional<Property> byId= propertyRepository.findById(propertyId); //it return thr optional class
        Property property=byId.get();
        Favourite favourite= new Favourite();
        favourite.setAppUser(user);
        favourite.setProperty(property);
        favourite.setIsFavourite(true);
       Favourite  savedfavourite= favouriteRepository.save(favourite);
       return new ResponseEntity<>(savedfavourite,HttpStatus.CREATED);

    }
    @GetMapping("/userFavouriteList")
    public ResponseEntity<List<Favourite>> getAllFavouritesOfUser(@AuthenticationPrincipal AppUser user){
     List<Favourite> favourite= favouriteRepository.getFavourite(user);
     return new ResponseEntity<>(favourite,HttpStatus.OK); //RTEURN THE LIST OF FAV PROPERTY

    }
}
