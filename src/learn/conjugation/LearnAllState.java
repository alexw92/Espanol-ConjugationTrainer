package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import dict.Dictionary;
import dict.Verb;

public class LearnAllState implements State {

	Random ran = new Random();
	private Dictionary dict;

	public LearnAllState(Dictionary dict) {
		this.dict = dict;
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		// Get random verb
		Verb v = getRandomVerb();
		// Get random sing/plural
		boolean plural = ran.nextBoolean();
		// Get random person
		int person = ran.nextInt(3) + 1;
		// "Wie lautet die 3.Person Singular Indefinido von acabar?"
		out.write("Wie lautet die " + person + ".Person " + ((plural) ? "Plural" : "Singular") + " Indefinido von "
				+ v.getInfinitve() + "?\n");
		out.flush();
		// Einlesen
		String inp = in.readLine();
		// Ergebnis auswerten
		String correct = v.getVerbForm(person, plural, Verb.INDEFINIDO);
		boolean quit = false;
		if (inp.equals(correct))
			;
		// Enter und q oder quit = Program verlassen
		else if (inp.equals("q") || inp.equals("quit")) {
			quit = true;
		}
		else{
			out.write("Falsch! Die korrekte Form lautet: " + correct + "!\n");
			out.flush();
		}
			if(!quit)
				ConjugationTrainer.changeState(this);
			else
				ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
	}

	private Verb getRandomVerb() {
		int i = ran.nextInt(dict.getVerbs().size());
		return dict.getVerbs().get(i);
	}

}
