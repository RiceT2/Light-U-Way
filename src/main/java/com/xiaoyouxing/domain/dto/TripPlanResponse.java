package com.xiaoyouxing.domain.dto;

import java.util.List;

public class TripPlanResponse {

    private String summary;
    private List<String> dayBoards;
    private List<String> transportOptions;
    private List<String> hotelOptions;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getDayBoards() {
        return dayBoards;
    }

    public void setDayBoards(List<String> dayBoards) {
        this.dayBoards = dayBoards;
    }

    public List<String> getTransportOptions() {
        return transportOptions;
    }

    public void setTransportOptions(List<String> transportOptions) {
        this.transportOptions = transportOptions;
    }

    public List<String> getHotelOptions() {
        return hotelOptions;
    }

    public void setHotelOptions(List<String> hotelOptions) {
        this.hotelOptions = hotelOptions;
    }
}
