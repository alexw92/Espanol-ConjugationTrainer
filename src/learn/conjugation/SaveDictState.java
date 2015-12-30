package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import dict.Dictionary;
import io.CSVExporter;

public class SaveDictState implements State {

	private Dictionary dict;
	public static String url = "testCSV.csv";
	
	public SaveDictState(Dictionary dict) {
		this.dict = dict;
	}
	
	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		CSVExporter csv = new CSVExporter();
		csv.saveVerbForms(url, dict.getVerbs());
		out.write("Wörterbuch gespeichert in \""+url+"\".");
		out.newLine();
		out.flush();
		ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
	}

}
