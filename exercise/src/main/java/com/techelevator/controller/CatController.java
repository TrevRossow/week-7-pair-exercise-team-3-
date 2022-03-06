package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatCardNotFoundException;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cards")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CatCard> list() {
        return this.catCardDao.list();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CatCard get(@PathVariable int id) {
        CatCard catCard = null;
        try {
            catCard = this.catCardDao.get(id);
        } catch (CatCardNotFoundException e) {
            System.out.println("cat card not found");
        }
        return catCard;
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public CatCard getRandom() {
        CatFact catFact = catFactService.getFact();
        CatPic catPic = catPicService.getPic();
        CatCard catCard = new CatCard();
        catCard.setCatFact(catFact.getText());
        catCard.setImgUrl(catPic.getFile());
        System.out.println(catCard);
        return catCard;
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean save(@Valid @RequestBody CatCard catCard) {
        return this.catCardDao.save(catCard);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public boolean update(@Valid @RequestBody CatCard catCard, @PathVariable int id) {
        return this.catCardDao.update(id, catCard);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable int id) {
        return this.catCardDao.delete(id);
    }


}
// GET /api/cards: Provides a list of all Cat Cards in the user's collection.
// GET /api/cards/{id}: Provides a Cat Card with the given ID.
// GET /api/cards/random: Provides a new, randomly created Cat Card containing information from the cat fact and picture services.
// POST /api/cards: Saves a card to the user's collection.
// PUT /api/cards/{id}: Updates a card in the user's collection.
// DELETE /api/cards/{id}: Removes a card from the user's collection.


// public class Reservation {

//     private int id;
//     @JsonProperty("hotelID")
//     private int hotelId;
//     private String fullName;