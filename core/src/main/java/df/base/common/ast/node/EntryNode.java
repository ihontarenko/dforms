package df.base.common.ast.node;

import df.base.common.ast.token.Token;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.container.StringUtils.underscored;

public class EntryNode extends AbstractNode {

    protected final Token.Entry         entry;
    protected final Map<String, Object> attributes = new HashMap<>();

    public EntryNode() {
        this(null);
    }

    public EntryNode(Token.Entry entry) {
        this.entry = entry;
    }

    public Map<String, Object> properties() {
        return this.attributes;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public Object setAttribute(String key, Object value) {
        return this.attributes.put(key, value);
    }

    public Token.Entry entry() {
        return this.entry;
    }

    @Override
    public String toString() {
        return String.format("%s ENTRY: [%s] PROPERTIES: %s", underscored(getClass().getSimpleName(), true),
                             entry, attributes);
    }

}
