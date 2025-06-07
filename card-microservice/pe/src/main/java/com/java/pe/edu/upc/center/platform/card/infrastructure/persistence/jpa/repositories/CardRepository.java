package com.java.pe.edu.upc.center.platform.card.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.java.pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import com.java.pe.edu.upc.center.platform.card.domain.model.valueobjects.CardNumber;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    boolean existsByCardNumber(CardNumber cardNumber);
    boolean existsByCardNumberAndIdIsNot(CardNumber cardNumber, int id);
    Optional<Card> findByCardNumber(CardNumber cardNumber);
    Optional<Card> findById(int id);
    Optional<List<Card>> getAllByProfileId(int profileId);
}

