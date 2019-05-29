package kt.entity;

public class LabelledToken {
	
	private String original;
	private String canonical;

	public LabelledToken(String original, String canonical) {
		this.original = original;
		this.canonical = canonical;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getCanonical() {
		return canonical;
	}

	public void setCanonical(String canonical) {
		this.canonical = canonical;
	}

}
