package com.xiaoyouxing.ai.agent;

import com.xiaoyouxing.domain.dto.TripPlanRequest;
import com.xiaoyouxing.function.hotel.HotelFunctionAdapter;
import com.xiaoyouxing.function.hotel.HotelSearchRequest;

import java.util.List;

public class HotelAgent {

    private final HotelFunctionAdapter hotelFunctionAdapter;

    public HotelAgent(HotelFunctionAdapter hotelFunctionAdapter) {
        this.hotelFunctionAdapter = hotelFunctionAdapter;
    }

    public List<String> recommend(TripPlanRequest request) {
        HotelSearchRequest query = new HotelSearchRequest();
        query.setCity(request.getDestination());
        query.setBudget(request.getHotelBudget());
        return hotelFunctionAdapter.searchHotels(query);
    }
}
