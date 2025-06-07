package com.java.pe.edu.upc.center.platform.card.domain.services;

import com.java.pe.edu.upc.center.platform.card.domain.model.commands.CreateCardCommand;
import com.java.pe.edu.upc.center.platform.card.domain.model.commands.DeleteCardCommand;
import com.java.pe.edu.upc.center.platform.card.domain.model.commands.UpdateCardCommand;
import com.java.pe.edu.upc.center.platform.card.domain.model.aggregates.Card;


import java.util.Optional;

public interface CardCommandService {
    int handle(CreateCardCommand command);
    Optional<Card> handle(UpdateCardCommand command);
    void handle(DeleteCardCommand command);
}

