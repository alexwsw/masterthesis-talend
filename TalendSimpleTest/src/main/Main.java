package main;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import elementParameter.EFieldTypes;
import elementParameter.ENames;
import elementParameter.ElementParameter;
import elementParameter.ElementParameterTable;
import elementValue.*;

public class Main {
	
	public static void main(String[] args) throws JAXBException {
		
	
		System.out.println(ElementValue.getInstance(EValueTypes.DELETE_KEY, true));
		System.out.println(ElementValue.getInstance(EValueTypes.KEY, "23"));
		System.out.println(ElementValue.getInstance(EValueTypes.INSERTABLE, false));
		System.out.println(ElementValue.getInstance(EValueTypes.VALUE, "Hello world"));
		
		System.out.println("**************************************");
		
		ElementParameterTable a = new ElementParameterTable(EFieldTypes.DUMMYTYPE, ENames.DUMMYNAME);
		a.addElementValue(ElementValue.getInstance(EValueTypes.INSERTABLE, false));
		a.addElementValue(ElementValue.getInstance(EValueTypes.TRACE_COLUMN, "Hello Fans"));
		System.out.println(a);
		System.out.println("**************************************");
		JAXBContext jb = JAXBContext.newInstance(ElementParameterTable.class);
		Marshaller marshaller = jb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(a, System.out);
	}
}
