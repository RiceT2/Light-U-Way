package com.xiaoyouxing.ai.orchestration;

import com.xiaoyouxing.ai.agent.HotelAgent;
import com.xiaoyouxing.ai.agent.PlannerAgent;
import com.xiaoyouxing.ai.agent.PoiAgent;
import com.xiaoyouxing.ai.agent.PolicyAgent;
import com.xiaoyouxing.ai.agent.TransportAgent;
import com.xiaoyouxing.domain.dto.TripPlanRequest;
import com.xiaoyouxing.domain.dto.TripPlanResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 多智能体编排骨架：
 * Planner -> (POI + Transport + Hotel) -> Policy -> Merge
 */
@Service
public class AiOrchestratorService {

    private final PlannerAgent plannerAgent;
    private final PoiAgent poiAgent;
    private final TransportAgent transportAgent;
    private final HotelAgent hotelAgent;
    private final PolicyAgent policyAgent;

    public AiOrchestratorService(PlannerAgent plannerAgent,
                                 PoiAgent poiAgent,
                                 TransportAgent transportAgent,
                                 HotelAgent hotelAgent,
                                 PolicyAgent policyAgent) {
        this.plannerAgent = plannerAgent;
        this.poiAgent = poiAgent;
        this.transportAgent = transportAgent;
        this.hotelAgent = hotelAgent;
        this.policyAgent = policyAgent;
    }

    public TripPlanResponse generatePlan(TripPlanRequest request) {
        List<String> dayBoards = plannerAgent.buildDayBoards(request);
        List<String> poiHighlights = poiAgent.recommendHighlights(request);
        List<String> transportOptions = transportAgent.recommend(request);
        List<String> hotelOptions = hotelAgent.recommend(request);
        String policy = policyAgent.audit(transportOptions, hotelOptions);

        TripPlanResponse response = new TripPlanResponse();
        response.setSummary("已生成「" + request.getDestination() + "」行程建议：" + policy);

        List<String> mergedBoards = new ArrayList<String>(dayBoards);
        mergedBoards.add("景点灵感：" + String.join(" / ", poiHighlights));
        response.setDayBoards(mergedBoards);

        response.setTransportOptions(transportOptions);
        response.setHotelOptions(hotelOptions);
        return response;
    }
}
