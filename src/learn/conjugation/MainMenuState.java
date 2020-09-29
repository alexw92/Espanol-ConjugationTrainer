package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class MainMenuState implements State {

	/**
	 * <h3>Aktuelle Features</h3>
	 * <ol>
	 * <li>Lernbox erstellen</li>
	 * <li>Lernbox laden</li>
	 * <li>Wort suchen</li>
	 * <li>Gesamtes W�rterbuch lernen</li>
	 * </ol>
	 * 
	 */
	@Override
	public void execute(BufferedReader in, BufferedWriter out)
			throws IOException {
		boolean fail = false;
		out.write("Konjugations-Trainer Hauptmenü\n" + "1: Lernbox erstellen\n"
				+ "2: Lernbox laden\n" + "3: Wort suchen\n"
				+ "4: Gesamtes Wörterbuch lernen\n"
				+ "5: Wörterbuch speichern\n" + "q: Programm beenden\n"
				+ "Bitte Option(Zahl) eingeben:");
		out.newLine();
		out.flush();
		String input = in.readLine();
		if (input.equals("q")) {
			ConjugationTrainer.changeState(ConjugationTrainer.quit);
		} else {
			int zahl = -1;
			try {
				zahl = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				fail = true;
			}
			if (!fail)
				switch (zahl) {
				case 1: {
					ConjugationTrainer
							.changeState(ConjugationTrainer.createBoxState);
					break;
				}
				case 2: {
					ConjugationTrainer
							.changeState(ConjugationTrainer.loadBoxState);
					break;
				}
				case 3: {
					ConjugationTrainer
							.changeState(ConjugationTrainer.searchWordState);
					break;
				}
				case 4: {
					ConjugationTrainer.changeState(ConjugationTrainer.init);
					break;
				}
				case 5: {
					ConjugationTrainer.changeState(ConjugationTrainer.saveDict);
					break;
				}
				default: {
					fail = true;
					break;
				}
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

}
