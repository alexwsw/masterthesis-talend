package auto;

import java.util.HashMap;
import java.util.Map;

import start.AutoDTO;

public class AutoManager {
	
	private Map <Integer,Auto> autos;
	private Integer anzahl = new Integer(1);
	
	public AutoManager() {
		this.autos = new HashMap<Integer,Auto>();
	}
	
	public void addAuto(AutoDTO auto) {
		Auto objAuto = new Auto(auto.marke, auto.typ);
		autos.put(anzahl, objAuto);
		anzahl++;
	}
	
	public void removeAuto(Integer nummer){
		if (findKey(nummer)) {
		autos.remove(nummer);
		} else {
			System.out.println("Key does not exist!");
		}
	}
	
	public boolean findKey (Integer nummer) {
		boolean gefunden = false;
		for (Integer iter : autos.keySet()) {
			if (nummer == iter) {
				gefunden = true;
				break;
			} 
		}
		return gefunden;
	}
	
	public Map <Integer, Auto> getAutos() {
		return autos;
	}
	

}
