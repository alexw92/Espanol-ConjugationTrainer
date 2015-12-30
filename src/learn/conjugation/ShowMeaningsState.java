package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import dict.Dictionary;
import dict.Verb;

public class ShowMeaningsState implements State {

	//TODO make operable from MainMenuState
	//TODO use this state in mainmenustate
	
	private Dictionary dict;
	// The verb whose meanings shall be printed
	private Verb verb;

	public ShowMeaningsState(Dictionary dict) {
		this.dict = dict;
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		ArrayList<String> ms = verb.getMeanings();
		if (ms.isEmpty()) {
			out.write(verb.getInfinitve() + " hat noch keine eingetragenen Bedeutungen:\n");
			out.newLine();
			out.flush();
		} else {
			out.write(verb.getInfinitve() + " hat folgende Bedeutungen:\n");
			out.newLine();
			out.flush();
			String means = "";
			for (int i = 0; i < ms.size(); i++) {
				// if not last meaning
				if (!(i == (ms.size() - 1))) {
					means += ms.get(i) + ", ";
				} else
					means += ms.get(i);

			}
			out.write(means);
			out.newLine();
			out.flush();
		}
		ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
	}

	public Verb getVerb() {
		return verb;
	}

	public void setVerb(Verb verb) {
		this.verb = verb;
	}


}
