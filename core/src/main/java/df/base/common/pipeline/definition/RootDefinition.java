package df.base.common.pipeline.definition;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.*;

@SuppressWarnings({"unused"})
public record RootDefinition(String name, Map<String, Chain> chains) {

    public RootDefinition {
        chains = new LinkedHashMap<>();
    }

    @JsonAnySetter
    public void addProcessChain(String name, Chain chain) {
        chains.put(chain.name(), chain);
    }

    public record Chain(String name, String initial, Map<String, Link> links) {

        public Chain {
            links = new HashMap<>();
        }

        @JsonAnySetter
        public void addProcessorLink(String name, Link link) {
            links.put(link.name(), link);
        }

    }

    public record Link(
            String name,

            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "processor")
            Processor processor,

            Map<String, String> transitions
    ) {

        public Link {
            transitions = new LinkedHashMap<>();
        }

        @JsonAnySetter
        public void addTransition(String name, Transition transition) {
            transitions.put(transition.returnCode(), transition.link());
        }

    }

    public record Processor(
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "class")
            String className,

            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "parameter")
            List<Parameter> parameters
    ) {}

    public record Transition(
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "return")
            String returnCode,

            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "link")
            String link
    ) {}

    public record Parameter(
            @JacksonXmlProperty(localName = "name")
            String name,

            @JacksonXmlProperty(localName = "value")
            String value,

            @JacksonXmlProperty(localName = "resolver")
            String resolver,

            @JacksonXmlProperty(localName = "converter")
            String converter
    ) {

    }

}
