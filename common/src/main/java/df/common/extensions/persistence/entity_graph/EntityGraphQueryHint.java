package df.common.extensions.persistence.entity_graph;

import jakarta.persistence.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public record EntityGraphQueryHint(EntityGraphType type,
                                   EntityGraph<?> entityGraph) { }
