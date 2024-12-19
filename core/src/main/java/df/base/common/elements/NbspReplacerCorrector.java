package df.base.common.elements;

import df.base.common.elements.node.TextNode;

public class NbspReplacerCorrector implements Corrector{

    @Override
    public void accept(Node node) {
       if (node instanceof TextNode textNode) {
           textNode.setText(textNode.getText().replaceAll("\s+", "&nbsp;"));
       }
    }

}
