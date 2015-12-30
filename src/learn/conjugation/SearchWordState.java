package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import dict.Dictionary;
import dict.Verb;

public class SearchWordState implements State {

	private Dictionary dict;

	public SearchWordState(Dictionary dict) {
		this.dict = dict;
	}
	
	@Override
	public void execute(BufferedReader in, BufferedWriter out) throws IOException {
		out.write("Geben Sie den Infinitv des Wortes ein, dass sie suchen(q zum Beenden):");
		out.newLine();
		out.flush();
		
		String word = in.readLine();
		Verb v = dict.getVerb(word);
		if(v!=null){
			out.write(v.getInfinitve()+" wurde im Wörterbuch gefunden. Wollen Sie die Formen ausgeben?(Y/N):");
			out.newLine();
			out.flush();
			String inp = in.readLine();
			if(!inp.equals("Y")){
				//Do nothing
			}
			else{
				out.write(v.toString());
				out.newLine();
				out.flush();
			}
		}
		else if(word.equals("q")){
			ConjugationTrainer.changeState(ConjugationTrainer.mainMenuState);
		}
		else{
			out.write(word+" wurde nicht im Wörterbuch gefunden");
			out.newLine();
			out.flush();
				
			}
		}
			
	

}
