package dict;

import java.util.ArrayList;

public class Verb {

	// Constant
	public static final int PRESENT = 0;
	public static final int INDEFINIDO = 1;
	public static final int PARTICIPLE = 2;
	public static final int FUTURO = 3;
	public static final int IMPERFECTO = 4;

	private String inf;
	private ArrayList<String> indefinido;
	private ArrayList<String> present;
	private ArrayList<String> futuro;
	private ArrayList<String> imperfecto;
	private String participle;
	private String gerund;

	public Verb(String infinitive) {
		this.inf = infinitive;
		indefinido = new ArrayList<String>();
		present = new ArrayList<String>();
		futuro = new ArrayList<String>();
		imperfecto = new ArrayList<String>();
	}

	public String getInfinitve() {
		return inf;
	}

	public void setParticiple(String participle) {
		this.participle = participle;
	}

	public String getParticiple() {
		return participle;
	}

	public String getVerbForm(int person, boolean plural, int tempus) {
		if (plural)
			person += 3;
		if (tempus == PRESENT)
			return present.get(person - 1);
		else if (tempus == INDEFINIDO)
			return indefinido.get(person - 1);
		else if (tempus == IMPERFECTO)
			return imperfecto.get(person - 1);
		else if (tempus == FUTURO)
			return futuro.get(person - 1);
		else
			return null;

	}

	public void setVerbForm(int person, boolean plural, int tempus, String form) {
		if (plural)
			person += 3;
		if (tempus == PRESENT) {
			present.add(person - 1, form);
			if (present.size() > 6)
				throw new DictionaryException("More than 6 Verbforms in tense " + tempus + ". (" + form + ", " + person
						+ ".Pers " + (plural ? "Plural" : "Singular") + ",)");
		} else if (tempus == INDEFINIDO) {
			indefinido.add(person - 1, form);
			if (present.size() > 6)
				throw new DictionaryException("More than 6 Verbforms in tense " + tempus + ". (" + form + ", " + person
						+ ".Pers " + (plural ? "Plural" : "Singular") + ",)");

		} else if (tempus == IMPERFECTO) {
			imperfecto.add(person - 1, form);
			if (present.size() > 6)
				throw new DictionaryException("More than 6 Verbforms in tense " + tempus + ". (" + form + ", " + person
						+ ".Pers " + (plural ? "Plural" : "Singular") + ",)");

		} else if (tempus == FUTURO) {
			futuro.add(person - 1, form);
			if (present.size() > 6)
				throw new DictionaryException("More than 6 Verbforms in tense " + tempus + ". (" + form + ", " + person
						+ ".Pers " + (plural ? "Plural" : "Singular") + ",)");

		} else
			System.out.println("Illegal Tempus form: " + tempus);

	}

	/**
	 * ... 4 = 1.Pers Plural; 5 = 2.Pers Plural; ...
	 * 
	 * @param number
	 */
	public void setVerbForm(int number, int tempus, String form) {
		if (number > 3)
			setVerbForm(number - 3, true, tempus, form);
		else
			setVerbForm(number, false, tempus, form);
	}

	public String toString() {
		String result = inf + "\n";
		if (present.size() != 0) {
			result += "Presente:\n";
			for (int i = 0; i < present.size(); i++) {
				result += present.get(i) + "\n";
			}
		}
		if (indefinido.size() != 0) {
			result += "Indefinido:\n";
			for (int i = 0; i < indefinido.size(); i++) {
				result += indefinido.get(i) + "\n";
			}
		}
		if (imperfecto.size() != 0) {
			result += "Imperfecto:\n";
			for (int i = 0; i < imperfecto.size(); i++) {
				result += imperfecto.get(i) + "\n";
			}
		}
		if (futuro.size() != 0) {
			result += "Futuro:\n";
			for (int i = 0; i < futuro.size(); i++) {
				result += futuro.get(i) + "\n";
			}
		}
		if (getParticiple() != null)
			result += "Participio: " + getParticiple() + "\n";
		if (getGerund() != null)
			result += "Gerundio: " + getGerund() + "\n";
		return result;
	}

	public int hashCode() {
		return inf.hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof Verb))
			return false;
		Verb v = (Verb) o;
		return this.getInfinitve().equals(v.getInfinitve());
	}

	public void setGerund(String gerund) {
		this.gerund = gerund;
	}

	public String getGerund() {
		return gerund;
	}

}
