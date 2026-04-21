package com.xiaoyouxing.agent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 参考 TravelAssistant 的 ToolCallAgent：
 * 在 ReAct 基础上增加工具注册和调用。
 */
public class ToolCallAgent extends ReActAgent {

    private final Map<String, Function<String, String>> tools = new LinkedHashMap<>();
    private String currentInput;
    private String preferredTool;

    public void setCurrentInput(String currentInput) {
        this.currentInput = currentInput;
    }

    public void setPreferredTool(String preferredTool) {
        this.preferredTool = preferredTool;
    }

    public void registerTool(String name, Function<String, String> toolInvoker) {
        tools.put(name, toolInvoker);
    }

    @Override
    protected String act(String thought) {
        if (preferredTool != null && tools.containsKey(preferredTool)) {
            String result = tools.get(preferredTool).apply(currentInput == null ? "" : currentInput);
            return "TERMINATE: " + preferredTool + " -> " + result;
        }
        return "TERMINATE: no tool selected, fallback summary -> " + thought;
    }
}
