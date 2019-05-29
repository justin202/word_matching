package kt.entity;

import java.util.ArrayList;

public class Token {
	
	private String original;
	private int distance;
	private String code;
	private ArrayList<String> canonical;
	
	public Token(String original, int distance, String code, ArrayList<String> canonical) {
		super();
		this.original = original;
		this.distance = distance;
		this.code = code;
		this.canonical = canonical;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<String> getCanonical() {
		return canonical;
	}

	public void setCanonical(ArrayList<String> canonical) {
		this.canonical = canonical;
	}

	@Override
	public String toString() {
		String ret = "";
		if(canonical == null || canonical.size() == 0)
			ret += original + " " + "NO" + " ";
		else{
			for(String c : canonical){
				if(code.length() == 0){
					if(c.equals(original))
						code = "IV";
					else
						code = "OOV";
				}
				ret += original + " " + code + " " + c + "\n";
			}
		}
		return ret;
	}
	
}
