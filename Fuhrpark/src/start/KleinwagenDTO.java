package start;

public class KleinwagenDTO extends AutoDTO {
	
	public double hubraum;

	public KleinwagenDTO(String marke, String typ, double hubraum) {
		super(marke, typ);
		this.hubraum = hubraum;
	}
	
	

}
