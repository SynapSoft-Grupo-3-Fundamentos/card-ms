package com.java.pe.edu.upc.center.platform.card.interfaces.rest.resources;

public record CardResource(int id, String number, String holder, int year, int month, String code, int paymentId, int profileId) {
}
