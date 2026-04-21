package com.xiaoyouxing.app;

import com.xiaoyouxing.agent.ToolCallAgent;
import com.xiaoyouxing.tools.TerminateTool;
import com.xiaoyouxing.tools.WebScrapingTool;
import com.xiaoyouxing.tools.WebSearchTool;
import org.springframework.stereotype.Service;

@Service
public class TravelAssistantApp {

    private final WebSearchTool webSearchTool = new WebSearchTool();
    private final WebScrapingTool webScrapingTool = new WebScrapingTool();
    private final TerminateTool terminateTool = new TerminateTool();

    public String chat(String prompt) {
        ToolCallAgent agent = new ToolCallAgent();
        agent.setObjective("为用户生成点击优先的旅行建议");
        agent.setCurrentInput(prompt);

        agent.registerTool("webSearch", webSearchTool::search);
        agent.registerTool("webScrape", webScrapingTool::scrape);
        agent.registerTool("terminate", terminateTool::terminate);

        if (prompt != null && prompt.startsWith("http")) {
            agent.setPreferredTool("webScrape");
        } else if (prompt != null && prompt.toLowerCase().contains("结束")) {
            agent.setPreferredTool("terminate");
        } else {
            agent.setPreferredTool("webSearch");
        }

        return agent.run(prompt == null ? "" : prompt);
    }
}
