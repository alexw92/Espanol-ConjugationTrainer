package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import dict.Verb;

public interface WordExporter {

	
	public void saveVerbForms(String url, ArrayList<Verb> verbs) throws FileNotFoundException;
	
	
	
	
}
