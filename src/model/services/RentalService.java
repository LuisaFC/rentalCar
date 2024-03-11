package model.services;

import model.entities.CartRental;
import model.entities.Invoice;

import java.time.Duration;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice(CartRental cartRental){
        double minutes = Duration.between(cartRental.getStart(), cartRental.getFinish()).toMinutes();
        double hours = minutes/60;

        double basicPayments;

        if(hours <= 12){
            basicPayments = pricePerHour * Math.ceil(hours);
        } else {
            basicPayments = pricePerDay * Math.ceil(hours / 24.0);
        }

        double tax = taxService.tax(basicPayments);

        cartRental.setInvoice(new Invoice(basicPayments, tax));
    }
}
