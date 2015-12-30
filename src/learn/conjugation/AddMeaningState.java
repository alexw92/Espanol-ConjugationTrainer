package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;

public class AddMeaningState implements State {

	private Dictionary dict;

	public AddMeaningState(Dictionary dict) {
		this.dict = dict;
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		out.write("Für welches Wort wollen Sie eine Bedeutung hinzufügen?");
		out.newLine();
		out.flush();
		String name = in.readLine();
		Verb v = dict.getVerb(name);
		if (v == null) {
			out.write(name + " konnte nicht im Wörterbuch gefunden werden!");
			out.newLine();
			out.flush();
			ConjugationTrainer.changeState(ConjugationTrainer.learnBoxState);
		} else {
			// Show meanings

			ArrayList<String> ms = v.getMeanings();
			if (ms.isEmpty()) {
				out.write(v.getInfinitve() + " hat noch keine eingetragenen Bedeutungen:");
				out.newLine();
				out.flush();
			} else {
				out.write(v.getInfinitve() + " hat folgende Bedeutungen:");
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

			// Add Meanings
			out.write("Geben Sie eine Bedeutung ein(q zum Beenden):");
			out.newLine();
			out.flush();
			String inp = in.readLine();
			if (inp.equals("q")) {
				// Do nothing, return to mother state
			}
			// Add Meaning
			else {
				v.addMeaning(inp);
				out.write("Bedeutung hinzugefügt. Im Hauptmenü können Sie diese Veränderung speichern:");
				out.newLine();
				out.flush();
			}
		}
		ConjugationTrainer.changeState(ConjugationTrainer.learnBoxState);
	}

}
