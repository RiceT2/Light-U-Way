package com.xiaoyouxing.api;

import com.xiaoyouxing.ai.orchestration.AiOrchestratorService;
import com.xiaoyouxing.app.TravelAssistantApp;
import com.xiaoyouxing.domain.dto.TripPlanRequest;
import com.xiaoyouxing.domain.dto.TripPlanResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlanController {

    private final AiOrchestratorService aiOrchestratorService;
    private final TravelAssistantApp travelAssistantApp;

    public PlanController(AiOrchestratorService aiOrchestratorService, TravelAssistantApp travelAssistantApp) {
        this.aiOrchestratorService = aiOrchestratorService;
        this.travelAssistantApp = travelAssistantApp;
    }

    @GetMapping("/bootstrap")
    public Map<String, Object> bootstrap() {
        Map<String, Object> data = new HashMap<>();
        data.put("welcome", "Hello，你想去哪儿玩？");
        data.put("quickDestinations", Arrays.asList("上海", "成都", "重庆", "三亚", "东京", "香港"));
        data.put("features", Arrays.asList(
                "少文字输入，全点击操作",
                "全屏热门目的地瀑布流",
                "半小时粒度时间选择",
                "景点拖拽式板式行程规划",
                "多智能体推荐交通、酒店、回程"
        ));
        return data;
    }

    @PostMapping("/plan/generate")
    public TripPlanResponse generate(@Validated @RequestBody TripPlanRequest request) {
        return aiOrchestratorService.generatePlan(request);
    }

    @GetMapping("/assistant/chat")
    public Map<String, String> chat(@RequestParam("message") String message) {
        Map<String, String> result = new HashMap<>();
        result.put("result", travelAssistantApp.chat(message));
        return result;
    }
}
