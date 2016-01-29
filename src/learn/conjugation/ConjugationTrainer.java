package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;
import io.CSVImporter;
import io.MyCSVImporter;

public class ConjugationTrainer {

	public static CreateBoxState createBoxState = null;
	public static LoadBoxState loadBoxState = null;
	public static SearchWordState searchWordState = null;
	public static LearnTenseState learnTenseState = null;
	public static  LearnBoxState learnBoxState = null;
	public static MainMenuState mainMenuState = null;
	public static LearnAllState init;
	public static State state;
	public static QuitState quit = new QuitState();
	public static LearnBox currentLearnBox = null;
	public static LearnAllTensesState learnAllTensesState;
	public static FrequentErrorState frequentState;
	public static AddMeaningState addMeaningState;
	public static ShowMeaningsState showMeaningsState;
	public static SaveDictState saveDict;
	public static LearnMeaningsState learnMeaningsState;
	
	public static final String testUrl = "testCSV.csv";
	public static final String realUrl = "spanisch verben.csv";

	
	public ConjugationTrainer(Dictionary dict) {
		init = new LearnAllState(dict);
		learnMeaningsState = new LearnMeaningsState(dict);
		mainMenuState = new MainMenuState();
		createBoxState = new CreateBoxState(dict);
		loadBoxState = new LoadBoxState(dict);
		learnBoxState = new LearnBoxState(dict);
		learnTenseState = new LearnTenseState(dict);
		learnAllTensesState = new LearnAllTensesState(dict);
		searchWordState = new SearchWordState(dict);
		frequentState = new FrequentErrorState(dict);
		addMeaningState = new AddMeaningState(dict);
		showMeaningsState = new ShowMeaningsState(dict);
		saveDict = new SaveDictState(dict);
	}

	public void startProgram(InputStream in, OutputStream out) {
		state = mainMenuState;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		while(!(state instanceof QuitState)){
			try {
				state.execute(reader, writer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer.write("Programm wird beendet.");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void changeState(State newState){
		state = newState;
	}
	
	public static void main(String[] args) {
		MyCSVImporter csv = new MyCSVImporter();
		ArrayList<Verb> verbs = new ArrayList<Verb>();
		try {
			csv.loadVerbForms(testUrl, verbs);
		//	csv.loadVerbForms(realUrl, verbs);
	    //	imp.loadVerbForms("perfekt.txt", Verb.PARTICIPLE, verbs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Dictionary dict = new Dictionary();
		dict.setVerbs(verbs);
		ConjugationTrainer trainer = new ConjugationTrainer(dict);
	    trainer.startProgram(System.in, System.out);
	//	for(Verb v : verbs)
	//		System.out.println(v);
	//	System.out.println(verbs.size());
	//	for(Verb v : verbs)
	//		System.out.println(v);
	//	CSVExporter exp = new CSVExporter();
	//	try {
	//		exp.saveVerbForms("Output.txt", verbs);
	//	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		
	}

	public static void setLearnBox(LearnBox box) {
		currentLearnBox  = box;
	}
	
	
	
	
	
	
	
	
	
	
	

}
