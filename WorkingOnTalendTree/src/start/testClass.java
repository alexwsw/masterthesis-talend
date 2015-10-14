package start;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import dto.AdvancedColumnDTO;

public class testClass {
	
	public static void doSomething(Document document, Node node) throws JAXBException {
		
		JAXBContext jb = JAXBContext.newInstance(AdvancedColumnDTO.class);
		Unmarshaller un = jb.createUnmarshaller();
		AdvancedColumnDTO column = (AdvancedColumnDTO)un.unmarshal(node);
		System.out.println(column);		
		
		column.setName("WASUUUUUUP");
		Marshaller ml = jb.createMarshaller();
		ml.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		ml.marshal(column, node.getParentNode());
		
	}

}
