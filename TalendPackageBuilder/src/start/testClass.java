package start;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import objects.ColumnDTO;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class testClass {
	
	public static void doSomething(Node node) throws JAXBException {
		
		JAXBContext jb = JAXBContext.newInstance(ColumnDTO.class);
		Unmarshaller un = jb.createUnmarshaller();
		ColumnDTO column = (ColumnDTO)un.unmarshal(node);
		System.err.println(column);		
		
		column.setName("WASUUUUUUP");
		Marshaller ml = jb.createMarshaller();
		ml.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		ml.marshal(column, node.getParentNode());
		ml.marshal(column, System.out);
		
	}

}
