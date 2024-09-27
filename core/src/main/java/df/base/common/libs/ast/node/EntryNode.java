package df.base.common.libs.ast.node;

import df.base.common.libs.ast.token.Token;
import df.base.common.libs.jbm.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.libs.jbm.StringUtils.underscored;

public class EntryNode extends AbstractNode {

    protected final Token.Entry         entry;
    protected final Map<String, Object> properties = new HashMap<>();

    public EntryNode(Token.Entry entry) {
        this.entry = entry;
    }

    public Map<String, Object> properties() {
        return this.properties;
    }

    public Object property(String key) {
        return this.properties.get(key);
    }

    public Object property(String key, Object value) {
        return this.properties.put(key, value);
    }

    public Token.Entry entry() {
        return this.entry;
    }

    @Override
    public String toString() {
        return String.format("%s ENTRY: [%s] PROPERTIES: %s", underscored(getClass().getSimpleName(), true),
                entry, properties);
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        throw new UnsupportedOperationException(
                "Evaluation is not supported for base class '%s'. It should be implemented in subclass '%s'.".formatted(
                        EntryNode.class, getClass()));
    }

}
