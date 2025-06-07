package com.java.pe.edu.upc.center.platform.card.interfaces.rest.resources;


public record CreateCardResource(String number, String holder, int year, int month, String code, int profileId, int paymentId) {
}
