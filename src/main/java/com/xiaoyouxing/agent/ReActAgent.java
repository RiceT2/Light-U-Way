package com.xiaoyouxing.agent;

public class ReActAgent extends BaseAgent {

    private String objective;

    public void setObjective(String objective) {
        this.objective = objective;
    }

    @Override
    protected String step() {
        String thought = think();
        return act(thought);
    }

    protected String think() {
        return "分析用户诉求并选择下一步动作: " + objective;
    }

    protected String act(String thought) {
        return "ACTION: " + thought;
    }
}
