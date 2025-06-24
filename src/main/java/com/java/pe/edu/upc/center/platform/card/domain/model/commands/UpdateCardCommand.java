package com.java.pe.edu.upc.center.platform.card.domain.model.commands;

public record UpdateCardCommand(int cardId, String number, String holder, int year,int month, String code, int paymentId, int profileId) {
}
