package start;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import auto.Kleinwagen;
import fuhrpark.*;

public class Main {
	
	public static void main(String[] args) throws JAXBException, ParseException {
		
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
		
		System.out.println(park.getAuto(miniC));
		System.out.println(miniC);
		
		JAXBContext jc = JAXBContext.newInstance(Fuhrpark.class);
		Marshaller marsh = jc.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marsh.marshal(park, System.out);
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		int a = 20141215;
		int year = a/10000;
		int month = (a%10000)/100;
		int day = a%100;
		System.out.println(day + "." + month + "." + year);
		Date date = new GregorianCalendar(year, month - 1, day).getTime();
		System.out.println(format.format(date));
		Date d2 = new GregorianCalendar(2015, 12-1, 10).getTime();
		String dateS = new SimpleDateFormat("yyyyMMdd").format(d2);
		int z = Integer.parseInt(dateS);
		Integer b = 34;
		String c = String.valueOf(b);
		System.out.println(z);
		
		
	}

}
