package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dict.Verb;

public class StrangeFileImporter implements WordImporter {

	ArrayList<Verb> words = new ArrayList<Verb>();

	@Override
	public void loadVerbForms(String url, ArrayList<Verb> verbs) throws FileNotFoundException {
		if (url.equals("indefinido.txt")) {
			int temp = Verb.INDEFINIDO;
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					if (line != "") {
						line = line.replaceAll("[\\s]+", ",");
						String[] split = line.split(",");
						Verb verb = new Verb(split[0]);
						if (verbs.contains(verb))
							verb = verbs.get(verbs.indexOf(verb));
						for (int i = 1; i < split.length; i++) {
							verb.setVerbForm(i, temp, split[i]);
						}
						verbs.add(verb);
					}

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (url.equals("perfekt.txt")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					if (line != "") {
						line = line.replaceAll("[\\s]+", ",");
						String[] split = line.split(",");
						System.out.println(line);
						Verb verb = new Verb(split[0]);
						if (verbs.contains(verb))
							verb = verbs.get(verbs.indexOf(verb));
						verb.setParticiple(split[1]);
						verbs.add(verb);
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
