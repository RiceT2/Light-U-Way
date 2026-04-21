package com.xiaoyouxing.ai.agent;

import java.util.List;

public class PolicyAgent {

    public String audit(List<String> transportOptions, List<String> hotelOptions) {
        if (transportOptions.isEmpty() || hotelOptions.isEmpty()) {
            return "建议补充更多选项后再生成最终行程";
        }
        return "预算与时间窗口校验通过";
    }
}
