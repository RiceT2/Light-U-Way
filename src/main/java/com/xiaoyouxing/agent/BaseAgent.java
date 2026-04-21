package com.xiaoyouxing.agent;

import com.xiaoyouxing.agent.model.AgentState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 参考 TravelAssistant 的 BaseAgent 设计：
 * 管理状态、消息内存、step 循环。
 */
public abstract class BaseAgent {

    private AgentState state = AgentState.IDLE;
    private int maxSteps = 6;
    private int currentStep = 0;
    private final List<String> memory = new ArrayList<>();

    public String run(String userPrompt) {
        if (userPrompt == null || userPrompt.trim().isEmpty()) {
            throw new IllegalArgumentException("userPrompt cannot be blank");
        }
        if (state != AgentState.IDLE) {
            throw new IllegalStateException("agent is not idle");
        }

        state = AgentState.RUNNING;
        memory.add("USER: " + userPrompt);

        List<String> results = new ArrayList<>();
        try {
            for (int i = 0; i < maxSteps && state == AgentState.RUNNING; i++) {
                currentStep = i + 1;
                String stepResult = step();
                results.add(stepResult);
                memory.add("STEP-" + currentStep + ": " + stepResult);
                if (shouldTerminate(stepResult)) {
                    state = AgentState.FINISHED;
                }
            }
            if (state == AgentState.RUNNING) {
                state = AgentState.FINISHED;
            }
        } catch (Exception ex) {
            state = AgentState.ERROR;
            memory.add("ERROR: " + ex.getMessage());
            throw ex;
        }
        return String.join("\n", results);
    }

    protected boolean shouldTerminate(String stepResult) {
        return stepResult != null && stepResult.startsWith("TERMINATE:");
    }

    public AgentState getState() {
        return state;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public List<String> getMemory() {
        return Collections.unmodifiableList(memory);
    }

    public void reset() {
        this.state = AgentState.IDLE;
        this.currentStep = 0;
        this.memory.clear();
    }

    protected abstract String step();
}
