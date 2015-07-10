package auto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Auto {

	private String marke;

	private String typ;
	
	public Auto (String marke, String typ) {
		
		this.marke = marke;
		this.typ = typ;
	}
	@XmlElement
	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}
	@XmlAttribute(name="Model")
	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	

}
