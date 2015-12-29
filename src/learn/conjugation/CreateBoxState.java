package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import dict.Dictionary;
import dict.LearnBox;
import dict.Verb;

public class CreateBoxState implements State {

	private Dictionary dict;
	public CreateBoxState(Dictionary dict) {
		this.dict = dict;
	}
	
	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		out.write("Wie soll die Lernbox heißen?\n");
		out.flush();
		String name = in.readLine();
		LearnBox box = new LearnBox(name, dict);
		out.write("Geben Sie die Infinitive der Verben ein, die Sie lernen möchten(q zum Beenden):\n");
		out.flush();
		String line = "";
		while(!((line = in.readLine())==null)){
			if(line.equals("q")){
				break;
			}
			Verb v = dict.getVerb(line);
			if(v==null){
				out.write("Wort "+line+" konnte nicht im Wörterbuch gefunden werden. Bitte wählen Sie ein anderes:");
				out.newLine();
				out.flush();
				continue;
			}
			else{
				box.addVerb(v);
				out.write("Wort "+v.getInfinitve()+" hinzugefügt. Weitere Wörter eingeben:");
				out.newLine();
				out.flush();
				continue;
			}
		}
		File folder = new File("LearnBoxes");
		boolean sameName = false;
		for(File f : folder.listFiles()){
			if(f.getName().endsWith(".box")&&f.getName().equals(name+".box"))
				sameName = true;
		}
		while(sameName == true){
			out.write("Es existiert bereits eine Lernbox mit diesem Namen. Bitte geben Sie einen anderen Namen ein:");
			out.newLine();
			out.flush();
			name = in.readLine();
			box.setName(name);
			sameName = false;
			for(File f : folder.listFiles()){
				if(f.getName().endsWith(".box")&&f.getName().equals(name+".box"))
					sameName = true;
			}
		}
		out.write("Lernbox "+name+" wurde mit "+box.getVerbs().size()+" Wörtern erstellt und gespeichert!");
		out.newLine();
		out.flush();
		box.saveLearnBox(null);
		ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
	}

}
