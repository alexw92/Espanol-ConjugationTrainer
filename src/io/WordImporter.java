package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import dict.Verb;

public interface WordImporter {

	public void loadVerbForms(String url, ArrayList<Verb> verbs) throws FileNotFoundException;
}
