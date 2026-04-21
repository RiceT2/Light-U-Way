package com.xiaoyouxing.ai.agent;

import com.xiaoyouxing.domain.dto.TripPlanRequest;

import java.util.Arrays;
import java.util.List;

public class PoiAgent {

    public List<String> recommendHighlights(TripPlanRequest request) {
        return Arrays.asList(
                request.getDestination() + "必打卡景点A（日出机位）",
                request.getDestination() + "必打卡景点B（夜景机位）"
        );
    }
}
