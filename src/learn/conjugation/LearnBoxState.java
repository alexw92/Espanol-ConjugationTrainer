package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;

public class LearnBoxState implements State {

	private Dictionary dict;

	public LearnBoxState(Dictionary dict) {
		this.dict = dict;
	}

	@Override
	/**
	 * Opionen für Lernbox Box1: 1. Wörter anzeigen 2. Wort hinzufügen 3. Nach
	 * Zeitformen lernen 4. Alle Formen lernen 5. Box löschen
	 */
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		LearnBox box = ConjugationTrainer.currentLearnBox;
		out.write("Optionen für Lernbox " + box.getName() + ":");
		out.newLine();
		out.write("1. Wörter anzeigen\n" + "2. Wort hinzufügen\n" + "3. Nach Zeitformen lernen\n"
				+ "4. Alle Formen lernen\n" + "5. Häufig falsche Wörter lernen\n" + "6. Box speichern\n"
				+ "7. Box löschen\n" + "q  Zurück zum Hauptmenü\n");
		out.flush();

		// Einlesen
		boolean fail = false;
		String input = in.readLine();
		if (input.equals("q") || input.equals("quit")) {
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
					// Zeige alle Wörter, bleibe in diesem Zustand
					out.write(box.toString());
					out.flush();
					break;
				}
				case 2: {
					out.write("Geben Sie das Wort ein, dass Sie hinzufügen wollen:");
					out.newLine();
					out.flush();
					String wort = in.readLine();
					Verb v = dict.getVerb(wort);
					if (v == null) {
						out.write("Das Wort " + wort + " konnte nicht gefunden werden");
						out.newLine();
						out.flush();
					} else {
						box.addVerb(v);
						out.write(wort + " befindet sich nun in Lernbox " + box.getName());
						out.newLine();
						out.flush();
					}
					break;
				}
				case 3: {
					ConjugationTrainer.changeState(ConjugationTrainer.learnTenseState);
					break;
				}
				case 4: {
					ConjugationTrainer.changeState(ConjugationTrainer.learnAllTensesState);
					break;
				}
				case 5: {
					box.refreshFrequentWrongVerbList();
					ConjugationTrainer.changeState(ConjugationTrainer.frequentState);
					break;
				}
				case 6: {
					box.saveLearnBox(null);
					out.write("Lernbox " + box.getName() + " mit " + box.getVerbs().size() + " Wörtern gespeichert!");
					out.newLine();
					out.flush();
					break;
				}
				case 7: {
					out.write("Wollen Sie die Lernbox " + box.getName() + " mit " + box.getVerbs().size()
							+ " Wörtern wirklich löschen?(Y/N)");
					out.newLine();
					out.flush();
					String s = in.readLine();
					boolean ioexception = false;
					if (s.equals("Y")) {
						File f = new File("LearnBoxes/" + box.getName() + ".box");
						if (!f.exists()) {
							out.write(f.getName() + " konnte nicht gefunden werden!");
							out.newLine();
							out.flush();
						} else {
							try {
								// Files.delete provides IOException if File
								// could not deleted
								Path p = FileSystems.getDefault().getPath("LearnBoxes/" + box.getName() + ".box");
								Files.delete(p);
							} catch (IOException e) {
								out.write("Lernbox " + box.getName()
										+ " konnte nicht gelöscht werden!(Auf Windows einfach die gewünschte File selbst im Ordner \"LearnBoxes\" Löschen)");
								out.newLine();
								out.flush();
								ioexception = true;
							}
							if (!ioexception) {
								out.write("Lernbox " + box.getName() + " gelöscht!");
								out.newLine();
								out.flush();
							}
						}
					} else {
						out.write("Löschvorgang abgebrochen!");
						out.newLine();
						out.flush();
					}
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
