package com.xiaoyouxing.ai.agent;

import com.xiaoyouxing.domain.dto.TripPlanRequest;

import java.util.Arrays;
import java.util.List;

public class PlannerAgent {

    public List<String> buildDayBoards(TripPlanRequest request) {
        return Arrays.asList(
                "第1天：核心地标 + 城市散步",
                "第2天：深度体验 + 夜景",
                "第3天：轻松收尾 + 返程"
        );
    }
}
