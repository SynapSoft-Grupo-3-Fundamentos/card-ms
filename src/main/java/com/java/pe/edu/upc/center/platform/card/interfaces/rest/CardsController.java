package com.java.pe.edu.upc.center.platform.card.interfaces.rest;



import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.java.pe.edu.upc.center.platform.card.domain.model.commands.DeleteCardCommand;
import com.java.pe.edu.upc.center.platform.card.domain.model.queries.GetAllCardsQuery;
import com.java.pe.edu.upc.center.platform.card.domain.model.queries.GetCardByIdQuery;
import com.java.pe.edu.upc.center.platform.card.domain.model.queries.GetCardByProfileId;
import com.java.pe.edu.upc.center.platform.card.domain.services.CardCommandService;
import com.java.pe.edu.upc.center.platform.card.domain.services.CardQueryService;
import com.java.pe.edu.upc.center.platform.card.interfaces.rest.resources.CardResource;
import com.java.pe.edu.upc.center.platform.card.interfaces.rest.resources.CreateCardResource;
import com.java.pe.edu.upc.center.platform.card.interfaces.rest.transform.CardResourceFromEntityAssembler;
import com.java.pe.edu.upc.center.platform.card.interfaces.rest.transform.CreateCardCommandFromResourceAssembler;
import com.java.pe.edu.upc.center.platform.card.interfaces.rest.transform.UpdateCardCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Cards", description = "Card Management Endpoint")
public class CardsController {

    private final CardQueryService cardQueryService;
    private final CardCommandService cardCommandService;


    public CardsController(CardQueryService cardQueryService, CardCommandService cardCommandService) {
        this.cardQueryService = cardQueryService;
        this.cardCommandService = cardCommandService;
    }

    @PostMapping
    public ResponseEntity<CardResource> createCard(@RequestBody CreateCardResource resource) {
        var createCardCommand = CreateCardCommandFromResourceAssembler.toCommandFromResource(resource);
        var cardId=this.cardCommandService.handle(createCardCommand);

        if(cardId == 0){
            return ResponseEntity.badRequest().build();
        }

        var getCardByIdQuery = new GetCardByIdQuery(cardId);
        var optionalCard = this.cardQueryService.handle(getCardByIdQuery);

        var cardResource = CardResourceFromEntityAssembler.toResourceFromEntity(optionalCard.get());
        return new ResponseEntity<>(cardResource, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<CardResource>> getAllCards() {
        var getAllCardsQuery = new GetAllCardsQuery();
        var cards = this.cardQueryService.handle(getAllCardsQuery);
        var cardResources = cards.stream().map(CardResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cardResources);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResource> getCardById(@PathVariable int cardId) {
        var getCardByIdQuery = new GetCardByIdQuery(cardId);
        var optionalCard = this.cardQueryService.handle(getCardByIdQuery);
        if(optionalCard.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var cardResource = CardResourceFromEntityAssembler.toResourceFromEntity(optionalCard.get());
        return ResponseEntity.ok(cardResource);
    }

    @GetMapping("get-by-profile-id/{profileId}")
    public ResponseEntity<List<CardResource>> getCardByUserId(@PathVariable int profileId) {
        var getCardByIdQuery = new GetCardByProfileId(profileId);
        var optionalCard = this.cardQueryService.handle(getCardByIdQuery);

        if(optionalCard.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var cardResource = optionalCard.get().stream().map(CardResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(cardResource);
    }

    @PutMapping("/{cardId}")
    public  ResponseEntity<CardResource> updateCard(@PathVariable int cardId, @RequestBody CardResource resource) {
        var updateCardCommand = UpdateCardCommandFromResourceAssembler.toCommandFromResource(cardId,resource);
        var optionalCard = this.cardCommandService.handle(updateCardCommand);

        if(optionalCard.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var cardResource = CardResourceFromEntityAssembler.toResourceFromEntity(optionalCard.get());
        return ResponseEntity.ok(cardResource);

    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable int cardId) {
        var deleteCardCommand= new DeleteCardCommand(cardId);
        this.cardCommandService.handle(deleteCardCommand);
        return ResponseEntity.noContent().build();
    }

}
