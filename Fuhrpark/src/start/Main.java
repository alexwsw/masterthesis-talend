package start;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String k = "2123.35";
		BigDecimal l = new BigDecimal(k);
		System.out.println(l);
		String datum = "2222-12/20";
		String pattern = "([0-9]{2})([\\/\\-\\.])([0-9]{2})([\\/\\-\\.])([0-9]{4})";
		System.out.println(datum.matches(pattern));
		System.out.println(datum.replaceAll("[\\/\\-\\.]", ""));
		String number = "1.002.134,33";
		String n = "100,000.00";
		String regex = "([1-9])([0-9]{0,2})(([\\,])([0-9]{3}))*(([\\.])([0-9])+)?";
		System.out.println(n.matches(regex));
		Object o = d2;
		System.out.println(o.toString());
		String string = "Hallo@TRIM";
		String pat = "@[A-Za-z]+";
		System.out.println(string.matches(pat));
		Pattern p = Pattern.compile(pat);
		Matcher m = p.matcher(string);
		if (m.find()){
			System.out.println(m.group().substring(1).toLowerCase());
		}
		String h = " FK_Bestellkopf";
		ArrayList <Object> ups = new ArrayList<Object>();
		String hi = "moin";
		ups.add(hi);
		for(Object ob : ups) {
			System.out.println(ob.getClass().getSimpleName().equals("String"));
			String oba = (String) ob;
			System.out.println(oba.substring(2));
		}
		/*
		try {
			Constructor i = Class.forName("blabla").getConstructor(String.class);
			Object o = i.newInstance("blabla");
			Method method = Class.forName("blabla").getMethod("convert", String.class);
			method.invoke(o, "blabla");
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	
		
	}

}
