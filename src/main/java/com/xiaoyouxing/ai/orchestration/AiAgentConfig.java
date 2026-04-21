package com.xiaoyouxing.ai.orchestration;

import com.xiaoyouxing.ai.agent.HotelAgent;
import com.xiaoyouxing.ai.agent.PlannerAgent;
import com.xiaoyouxing.ai.agent.PoiAgent;
import com.xiaoyouxing.ai.agent.PolicyAgent;
import com.xiaoyouxing.ai.agent.TransportAgent;
import com.xiaoyouxing.function.hotel.HotelFunctionAdapter;
import com.xiaoyouxing.function.transport.TransportFunctionAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiAgentConfig {

    @Bean
    public PlannerAgent plannerAgent() {
        return new PlannerAgent();
    }

    @Bean
    public PoiAgent poiAgent() {
        return new PoiAgent();
    }

    @Bean
    public PolicyAgent policyAgent() {
        return new PolicyAgent();
    }

    @Bean
    public TransportFunctionAdapter transportFunctionAdapter() {
        return new TransportFunctionAdapter();
    }

    @Bean
    public HotelFunctionAdapter hotelFunctionAdapter() {
        return new HotelFunctionAdapter();
    }

    @Bean
    public TransportAgent transportAgent(TransportFunctionAdapter adapter) {
        return new TransportAgent(adapter);
    }

    @Bean
    public HotelAgent hotelAgent(HotelFunctionAdapter adapter) {
        return new HotelAgent(adapter);
    }
}
