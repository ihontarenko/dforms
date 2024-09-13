package df.base.common.libs.ast.node;

import df.base.common.libs.ast.token.Token;

import java.util.HashMap;
import java.util.Map;

public class EntryNode extends AbstractNode {

    private final Token.Entry         entry;
    private final Map<String, Object> attributes = new HashMap<>();

    public EntryNode(Token.Entry entry) {
        this.entry = entry;
    }

    public Map<String, Object> properties() {
        return this.attributes;
    }

    public Object property(String key) {
        return this.attributes.get(key);
    }

    public Token.Entry entry() {
        return this.entry;
    }

    @Override
    public String toString() {
        return String.format("NODE V:[%s] ATTRIBUTES[%s]", entry, attributes);
    }

}
