package com.java.pe.edu.upc.center.platform.card.domain.model.commands;

public record CreateCardCommand(int userId, String number, String holder, int year,int month, String code, int paymentId) {
}
