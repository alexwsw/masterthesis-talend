package auto;

import javax.xml.bind.annotation.XmlElement;

public class Kleinwagen extends Auto{
	
	private double hubraum;

	public Kleinwagen(String marke, String typ, double hubraum) {
		super(marke, typ);
		this.hubraum = hubraum;
	}
	
	@XmlElement (name="Hubraum", required=true)
	public double getHubraum() {
		return this.hubraum;
	}

	public void setHubraum(double hubraum) {
		this.hubraum = hubraum;
	}
	
	public String toString(){
		return String.format("Marke: %s, Modell: %s, Hubraum: %s ", this.getMarke(), this.getTyp(), this.getHubraum());
	}
	

	

}
