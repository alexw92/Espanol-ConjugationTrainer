package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import dict.Dictionary;
import dict.LearnBox;

public class LoadBoxState implements State {

	private Dictionary dict;

	public LoadBoxState(Dictionary dict) {
		this.dict = dict;
	}

	@Override
	public void execute(BufferedReader in, BufferedWriter out)
			throws IOException {
		File fo = new File("LearnBoxes");
		ArrayList<String> learnboxes = new ArrayList<String>();
		for (File file : fo.listFiles()) {
			if (file.getName().endsWith(".box")) {
				learnboxes.add(file.getName().substring(0,
						file.getName().length() - 4));
			}
		}
		String firstBox = "";
		if (learnboxes.size() == 0) {
			out.write("Es konnten keine Lernboxen im Ordner " + fo.getName()
					+ " gefunden werden.\n "
					+ "Bitte erstellen Sie im Hauptmenü eine neue Lernbox");
			out.newLine();
			out.flush();
			ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
		} else {
			firstBox = learnboxes.get(0);
			out.write("Es "
					+ ((learnboxes.size() == 1) ? "konnte folgende Lernbox gefunden werden:"
							: "konnten folgende Lernboxen gefunden werden:"));
			out.newLine();
			out.flush();

			for (String s : learnboxes) {
				out.write(s);
				out.newLine();
				out.flush();
			}
			out.write("Welche Lernbox wollen Sie laden?(" + firstBox + ")");
			out.newLine();
			out.flush();
			String boxname = in.readLine();
			String name = null;
			boolean wrong = true;
			LearnBox box = null;
			// Enter = LoadDefault Box
			if (boxname.equals("") && firstBox != "") {
				boxname = firstBox;
				name = fo.getName() + "/" + firstBox + ".box";
				box = LearnBox.loadLearnBox(name, dict);
			} else
				while (wrong) {
					try {
						name = fo.getName() + "/" + boxname + ".box";
						box = LearnBox.loadLearnBox(name, dict);
					} catch (FileNotFoundException e) {
						out.write(name
								+ " konnte nicht gefunden werden. Bitte geben Sie den Namen einer existenten Lernbox ein:");
						out.newLine();
						out.flush();
						boxname = in.readLine();
						continue;
					}
					wrong = false;
				}

			out.write(boxname + " wurde erfolgreich geladen!");
			out.newLine();
			out.flush();
			ConjugationTrainer.setLearnBox(box);
			ConjugationTrainer.changeState(ConjugationTrainer.learnBoxState);
		}
	}

}
