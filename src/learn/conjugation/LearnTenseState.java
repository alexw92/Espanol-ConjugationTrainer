package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;

public class LearnTenseState implements State {

	private Dictionary dict;
	private Random ran;
	private int nextVerb;
	// Number of verbs correct in a row in this session
	private int sessionHighscore;
	private int currentHighscore;
	private static int sessionHighscoreConstant = 2;

	public LearnTenseState(Dictionary dict) {
		this.dict = dict;
		ran = new Random();
		this.nextVerb = -1;
		sessionHighscore = sessionHighscoreConstant;
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out)
			throws IOException {
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
		boolean fail = false;
		String input = in.readLine();
		if (input.equals("q")) {
			ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
		} else {
			int zahl = -1;
			try {
				zahl = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				fail = true;
			}
			if (!fail && zahl >= 0 && zahl < dict.getTenses().size()) {
				out.write("Welche Zeit wollen Sie lernen?(Zahl eingeben)");
				out.newLine();
				out.flush();
				// Lerne endlos bis exit(also q oder quit als Eingabe) kommt
				while (!askTenseFormAllForms(in, out, zahl,
						ConjugationTrainer.currentLearnBox))
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
				ConjugationTrainer.changeState(this);
			}

		}
	}

	private Verb getRandomVerb() {
		int i = ran.nextInt(ConjugationTrainer.currentLearnBox.getVerbs()
				.size());
		return ConjugationTrainer.currentLearnBox.getVerbs().get(i);
	}

	private Verb getNextVerb() {
		return ConjugationTrainer.currentLearnBox.getVerbs().get(
				(++nextVerb)
						% ConjugationTrainer.currentLearnBox.getVerbs().size());
	}

	/**
	 * 
	 * @param in
	 * @param out
	 * @param tense
	 * @return true if the user exits this program
	 * @throws IOException
	 */
	private boolean askTenseForm(BufferedReader in, BufferedWriter out,
			int tense, LearnBox box) throws IOException {
		// Get random verb
		Verb v = getRandomVerb();
		// Get random sing/plural
		boolean plural = ran.nextBoolean();
		// Get random person
		int person = ran.nextInt(3) + 1;
		// "Wie lautet die 3.Person Singular Indefinido von acabar?"
		out.write("Wie lautet die "
				+ person
				+ ".Person "
				+ ((plural) ? "Plural, " : "Singular, ")
				+ dict.getTenses().get(tense)
				+ " von "
				+ v.getInfinitve()
				+ ((v.getMeaningsString() != null) ? "("
						+ v.getMeaningsString() + ")" : "") + "?\n");
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
		Verb v = getNextVerb();
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
			}
		} else
			currentHighscore = 0;

		return false;
	}

}
