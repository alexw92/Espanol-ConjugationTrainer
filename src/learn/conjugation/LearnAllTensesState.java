package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;

public class LearnAllTensesState implements State {

	private Dictionary dict;
	private Random ran;

	public LearnAllTensesState(Dictionary dict) {
		this.dict = dict;
		ran = new Random();
	}
	
	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		//Einführungstext
		out.write("Lernmodus \"Alle Zeiten\" betreten. Geben Sie zum Verlassen \"q\" ein.");
		out.newLine();
		out.flush();
		//get random tense
		int zahl = ran.nextInt(dict.getTenses().size());
		while (!askTenseForm(in, out, zahl, ConjugationTrainer.currentLearnBox))
			//new random tense
			zahl = ran.nextInt(dict.getTenses().size());
		
		//Nach verlassen des Lernmodus, Ergebnisse speichern
		ConjugationTrainer.currentLearnBox.saveLearnBox(null);
		out.write("Lernmodus \"Alle Zeiten\" verlassen. Ergebnisse wurden gespeichert.");
		out.newLine();
		out.flush();
		ConjugationTrainer.changeState(ConjugationTrainer.learnBoxState);
	}
	
	private Verb getRandomVerb() {
		int i = ran.nextInt(ConjugationTrainer.currentLearnBox.getVerbs().size());
		return ConjugationTrainer.currentLearnBox.getVerbs().get(i);
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
		Verb v = getRandomVerb();
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
			//Fehler in Lernbox vermerken
			box.addErrorCount(v);
		}
		return false;
	}


	
}
