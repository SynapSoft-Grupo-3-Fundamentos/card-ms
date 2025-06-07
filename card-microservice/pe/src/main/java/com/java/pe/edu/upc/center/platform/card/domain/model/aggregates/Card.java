package com.java.pe.edu.upc.center.platform.card.domain.model.aggregates;

import com.java.pe.edu.upc.center.platform.card.domain.model.commands.CreateCardCommand;
import com.java.pe.edu.upc.center.platform.card.domain.model.commands.UpdateCardCommand;
import com.java.pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

import  com.java.pe.edu.upc.center.platform.card.domain.model.valueobjects.CardNumber;
import com.java.pe.edu.upc.center.platform.card.domain.model.valueobjects.CardHolder;
import com.java.pe.edu.upc.center.platform.card.domain.model.valueobjects.ExpirationDate;
import com.java.pe.edu.upc.center.platform.card.domain.model.valueobjects.Cvv;





@Entity
@Table(name = "cards")
public class Card extends AuditableAbstractAggregateRoot<Card> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cardNumber", column = @Column(name = "card_number", length = 16,nullable = false)),
    })
    private CardNumber cardNumber;

    @Column(nullable = false)
    private int profileId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cardHolder", column = @Column(name = "card_holder", length = 50,nullable = false)),
    })
    private CardHolder cardHolder;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "year", column = @Column(name = "expiration_year", nullable = false)),
            @AttributeOverride(name = "month", column = @Column(name = "expiration_month", nullable = false)),
    })
    private ExpirationDate expirationDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cvv", column = @Column(name = "cvv",length = 3, nullable = false)),
    })
    private Cvv cvv;

    @Column(nullable = false)
    private int paymentId;

    public Card() {

    }

    public Card(String number, String holder,int year, int month, String code, int paymentId) {
        this.cardNumber = new CardNumber(number);
        this.cardHolder = new CardHolder(holder);
        this.expirationDate = new ExpirationDate(year, month);
        this.cvv = new Cvv(code);
        this.paymentId = paymentId;
    }

    public void updateCardNumber(String number) {
        this.cardNumber = new CardNumber(number);
    }

    public String getCardNumber(){
        return cardNumber.number();
    }
    public void updateCardHolder(String holder) {
        this.cardHolder = new CardHolder(holder);
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getCardHolder(){
        return cardHolder.holder();
    }

    public void updateExpirationDate(int year, int month) {
        this.expirationDate = new ExpirationDate(year, month);
    }

    public int getExpirationYear() {
        return expirationDate.year();
    }

    public int getExpirationMonth() {
        return expirationDate.month();
    }
    public void updateCvv(String code) {
        this.cvv = new Cvv(code);
    }
    public String getCvv(){
        return cvv.code();
    }

    public int getId() {
        return super.getId();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }


    public Card(CreateCardCommand command) {
        this.cardNumber= new CardNumber(command.number());
        this.cardHolder= new CardHolder(command.holder());
        this.expirationDate=new ExpirationDate(command.year(), command.month());
        this.cvv=new Cvv(command.code());
        this.profileId = command.userId();
        this.paymentId = command.paymentId();
    }

    public void updateInformation(UpdateCardCommand command) {
        this.cardNumber= new CardNumber(command.number());
        this.cardHolder= new CardHolder(command.holder());
        this.expirationDate=new ExpirationDate(command.year(), command.month());
        this.cvv=new Cvv(command.code());
        this.paymentId = command.paymentId();
        this.profileId = command.profileId();
    }


}