package dict;

import java.util.ArrayList;

public class Dictionary {

	// Attributes
	ArrayList<Verb> verbs = new ArrayList<Verb>();
	ArrayList<String> tenses = new ArrayList<String>();
	public Dictionary() {
		tenses.add("Presente");
		tenses.add("Indefinido");
		tenses.add("Participio");
		tenses.add("Futuro");
		tenses.add("Imperfecto");
	}
	
	public ArrayList<String> getTenses(){
		return tenses;
	}

	public void setVerbs(ArrayList<Verb> verbs) {
		this.verbs = verbs;
	}

	public ArrayList<Verb> getVerbs() {
		return verbs;
	}

	public static void main(String[] args) {

	}

	public Verb getVerb(String inf) {
		Verb v = new Verb(inf);
		if (verbs.contains(v))
			return verbs.get(verbs.indexOf(v));
		else
			return null;
	}

}
