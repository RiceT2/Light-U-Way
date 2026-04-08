package com.xiaoyouxing.domain.dto;

import javax.validation.constraints.NotBlank;

public class TripPlanRequest {

    @NotBlank
    private String destination;
    @NotBlank
    private String departureTime;
    private Integer hotelBudget;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getHotelBudget() {
        return hotelBudget;
    }

    public void setHotelBudget(Integer hotelBudget) {
        this.hotelBudget = hotelBudget;
    }
}
