package df.base.common.pipeline;

import java.util.Map;

public record Transitions(String initial, Map<String, String> transitions) {

    public String getInitial() {
        return initial;
    }

    public String getNextFor(String returnCode) {
        return transitions.get(returnCode);
    }

}