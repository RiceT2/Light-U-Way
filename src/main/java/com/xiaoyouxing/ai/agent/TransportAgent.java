package com.xiaoyouxing.ai.agent;

import com.xiaoyouxing.domain.dto.TripPlanRequest;
import com.xiaoyouxing.function.transport.TransportFunctionAdapter;
import com.xiaoyouxing.function.transport.TransportSearchRequest;

import java.util.List;

public class TransportAgent {

    private final TransportFunctionAdapter transportFunctionAdapter;

    public TransportAgent(TransportFunctionAdapter transportFunctionAdapter) {
        this.transportFunctionAdapter = transportFunctionAdapter;
    }

    public List<String> recommend(TripPlanRequest request) {
        TransportSearchRequest query = new TransportSearchRequest();
        query.setOrigin("当前城市");
        query.setDestination(request.getDestination());
        query.setDepartTime(request.getDepartureTime());
        return transportFunctionAdapter.searchTransport(query);
    }
}
