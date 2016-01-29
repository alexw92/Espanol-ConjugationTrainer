package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import dict.Dictionary;

public class LearnMeaningsState implements State {

	private Dictionary dict;

	public LearnMeaningsState(Dictionary dict) {
		this.dict = dict;
	}
	
	@Override
	public void execute(BufferedReader in, BufferedWriter out)
			throws IOException {
		
		

	}
	
	

}
