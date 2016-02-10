package main;

import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import node.AbstractNode;
import node.INode;
import node.tMSSqlConnection;
import node.tPreJob;
import subjob.Subjob;
import talendFile.ProcessType;
import elementParameter.EFieldTypes;
import elementParameter.ENames;
import elementParameter.ElementParameter;
import elementParameter.ElementParameterTable;
import elementValue.*;

public class Main {
	
	public static void main(String[] args) throws JAXBException {
		
		//path for the test xml file if required
		String path = "C:\\Users\\becher\\Desktop\\testfile.item";
		
	
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
	
		ElementParameter b = new ElementParameter(EFieldTypes.DUMMYTYPE, ENames.DUMMYNAME, "23");
		System.out.println(b);
		System.out.println("========================================");
		Subjob c = new Subjob();
		c.addElementParameter(a);
		c.addElementParameter(b);
		System.out.println(c);
		
		INode node = new tMSSqlConnection();
		INode node2 = new tMSSqlConnection();
		INode pre = new tPreJob();
		
		ProcessType type = new ProcessType();
		type.addNode(node);
		type.addNode(node2);
		type.addNode(pre);
		
		System.out.println(node);
		
		
		
		//Marshalling
		JAXBContext jb = JAXBContext.newInstance(ProcessType.class, AbstractNode.class);
		Marshaller marshaller = jb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(type, System.out);
		
		
	
		/*Marshalling subjob
		jb = JAXBContext.newInstance(Subjob.class);
		Marshaller m2 = jb.createMarshaller();
		m2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m2.marshal(c, System.out);
		*/
	}
}
