package start;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import auto.Kleinwagen;
import fuhrpark.*;

public class Main {
	
	public static void main(String[] args) throws JAXBException {
		
		AutoDTO bmw = new AutoDTO("BMW", "M3");
		AutoDTO mers = new AutoDTO("Mercedes-Benz", "S-500");
		AutoDTO man = new AutoDTO("MAN", "sehr gross");
		KleinwagenDTO mini = new KleinwagenDTO("MINI", "Cooper", 2.5);
		Kleinwagen miniC = new Kleinwagen(mini.marke, mini.typ, mini.hubraum);
		
		
		Fuhrpark park = new Fuhrpark();
		park.addAuto(bmw);
		park.addAuto(mers);
		park.addAuto(man);
		park.addAuto(mini);
		
		System.out.println(miniC);
		
		JAXBContext jc = JAXBContext.newInstance(Fuhrpark.class, Kleinwagen.class);
		Marshaller marsh = jc.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marsh.marshal(park, System.out);
		
	}

}
