package df.base.common.elements.node;

import df.base.common.elements.TagName;

public class HTMLElementNode extends ElementNode {

    public HTMLElementNode(TagName tagName) {
        super(tagName);
    }

    public void setId(String id) {
        addAttribute("id", id);
    }

    public void setClass(String classNames) {
        addAttribute("class", classNames);
    }

}
