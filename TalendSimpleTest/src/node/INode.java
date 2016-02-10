package node;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import element.IElement;
@XmlJavaTypeAdapter(node.AbstractNode.Adapter.class)
public interface INode extends IElement{

}
