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
	private int currentHighscore = 0;
	private int sessionHighscore = 0;

	public FrequentErrorState(Dictionary dict) {
		this.dict = dict;
		this.ran = new Random();
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		this.box = ConjugationTrainer.currentLearnBox;
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
			return;
		} else {
			try {
				zeit = Integer.parseInt(tenseNumber);
			} catch (NumberFormatException e) {
				failNumber = true;
			}
			if (!failNumber && zeit >= 0 && zeit < dict.getTenses().size()) {
				// Lerne endlos bis exit(also q oder quit als Eingabe) kommt
				while (!askTenseFormAllForms(in, out, zeit, ConjugationTrainer.currentLearnBox))
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
		
		if(v==null){
			out.write("Keine häufig falschen W�rter mehr in der Liste!");
			out.newLine();
			out.flush();
			return true;
		}
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
		    //	box.addErrorCount(v);
		}
		return false;
	}
	

	/**
	 * Difference to askTenseForm(): All verb forms of the specific tense need
	 * to be entered
	 * 
	 * @param in
	 * @param out
	 * @param tense
	 * @param box
	 * @return
	 * @throws IOException
	 */
	private boolean askTenseFormAllForms(BufferedReader in, BufferedWriter out,
			int tense, LearnBox box) throws IOException {
		// Get random verb
		// Verb v = getRandomVerb();
		// Get next verb
		// Get random verb
		Verb v = box.getFrequentWrongVerb();
		
		if(v==null){
			out.write("Keine häufig falschen Wörter mehr in der Liste!");
			out.newLine();
			out.flush();
			return true;
		}
		
		int person = 1;
		boolean plural = false;
		boolean allFormsCorrect = true;
		for (int i = 1; i < 7; i++) {

			if (i > 3) {
				person = i - 3;
				plural = true;

			} else {
				person = i;
				plural = false;
			}
			out.write(person
					+ ".Person "
					+ ((plural) ? "Plural, " : "Singular, ")
					+ dict.getTenses().get(tense)
					+ " von "
					+ v.getInfinitve()
					+ ((v.getMeaningsString() != null) ? "("
							+ v.getMeaningsString() + ")" : "") + ":");
			out.flush();
			// Einlesen
			String inp = in.readLine();
			// Ergebnis auswerten
			String correct = v.getVerbForm(person, plural, tense);
			if (inp.equals(correct)) {

			}

			else if (inp.equals("q") || inp.equals("quit")) {
				// exit learn mode
				return true;
			} else if (inp.equals("n")) {
				// jump to next verb
				return false;
			}

			else {
				out.write("Falsch! Korrekte Form: " + correct + "!");
				out.newLine();
				out.flush();
				allFormsCorrect = false;
				// Fehler in Lernbox vermerken
				box.addErrorCount(v);
			}
		}
		if (allFormsCorrect) {
			box.decreaseErrorCount(v);
			currentHighscore++;
			if (sessionHighscore < currentHighscore) {
				sessionHighscore = currentHighscore;
				out.write("Neuer Session-Highscore! Schon " + sessionHighscore
						+ " Verben hintereinander richtig!");
				out.newLine();
				out.flush();
				//refresh needed as the wrong number of one verb has been decreased
				box.refreshFrequentWrongVerbList();
			}
		} else
			currentHighscore = 0;

		return false;
	}
	



}
