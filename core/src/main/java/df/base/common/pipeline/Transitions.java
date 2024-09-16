package df.base.common.pipeline;

import java.util.Map;

public class Transitions {

    private final String              initial;
    private final Map<String, String> transitions;

    public Transitions(String initial, Map<String, String> transitions) {
        this.initial = initial;
        this.transitions = transitions;
    }

    public String getInitial() {
        return initial;
    }

    public String getNextFor(String returnCode) {
        return transitions.get(returnCode);
    }
}