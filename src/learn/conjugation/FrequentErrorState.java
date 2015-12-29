package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;

public class FrequentErrorState implements State {

	private LearnBox box;
	private Dictionary dict;
	private Random ran;

	public FrequentErrorState(Dictionary dict) {
		this.box = ConjugationTrainer.currentLearnBox;
		this.dict = dict;
		this.ran = new Random();
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		out.write("Welche Zeit wollen Sie lernen?(Zahl eingeben)");
		out.newLine();
		out.flush();
		for (int i = 0; i < dict.getTenses().size(); i++) {
			out.write(i + ". " + dict.getTenses().get(i));
			out.newLine();
		}
		out.newLine();
		out.flush();
		// Einlesen der Wahl der Zeit
		boolean failNumber = false;
		String tenseNumber = in.readLine();
		int zeit = -1;
		if (tenseNumber.equals("q")) {
			ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
		} else {
			try {
				zeit = Integer.parseInt(tenseNumber);
			} catch (NumberFormatException e) {
				failNumber = true;
			}
			if (!failNumber && zeit >= 0 && zeit < dict.getTenses().size()) {
				// Lerne endlos bis exit(also q oder quit als Eingabe) kommt
				while (!askTenseForm(in, out, zeit, ConjugationTrainer.currentLearnBox))
					;
				// Nach verlassen des Lernmodus, Ergebnisse speichern
				ConjugationTrainer.currentLearnBox.saveLearnBox(null);
				out.write("Lernmodus verlassen. Ergebnisse wurden gespeichert.");
				out.newLine();
				out.flush();
			}

			else {
				out.write("Falsche Eingabe! Die Zahl muss einer der obigen Optionen entsprechen.");
				out.newLine();
				out.flush();
				// Verbleibe im Zustand
				return;
			}

		}
		box.refreshFrequentWrongVerbList();
		while (!askTenseForm(in, out, zeit, ConjugationTrainer.currentLearnBox))
			;
		// Nach verlassen des Lernmodus, Ergebnisse speichern
		ConjugationTrainer.currentLearnBox.saveLearnBox(null);
		out.write("Lernmodus verlassen. Ergebnisse wurden gespeichert.");
		out.newLine();
		out.flush();

	}

	/**
	 * 
	 * @param in
	 * @param out
	 * @param tense
	 * @return true if the user exits this program
	 * @throws IOException
	 */
	private boolean askTenseForm(BufferedReader in, BufferedWriter out, int tense, LearnBox box) throws IOException {
		// Get random verb
		Verb v = box.getFrequentWrongVerb();
		// Get random sing/plural
		boolean plural = ran.nextBoolean();
		// Get random person
		int person = ran.nextInt(3) + 1;
		// "Wie lautet die 3.Person Singular Indefinido von acabar?"
		out.write("Wie lautet die " + person + ".Person " + ((plural) ? "Plural, " : "Singular, ")
				+ dict.getTenses().get(tense) + " von " + v.getInfinitve() + "?\n");
		out.flush();
		// Einlesen
		String inp = in.readLine();
		// Ergebnis auswerten
		String correct = v.getVerbForm(person, plural, tense);
		if (inp.equals(correct))
			;
		// Enter und q oder quit = Program verlassen
		else if (inp.equals("q") || inp.equals("quit")) {
			return true;
		} else {
			out.write("Falsch! Die korrekte Form lautet: " + correct + "!\n");
			out.flush();
			// Fehler in Lernbox vermerken
			box.addErrorCount(v);
		}
		return false;
	}

}
