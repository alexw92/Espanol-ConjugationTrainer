package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dict.Verb;

public class CSVImporter implements WordImporter {

	ArrayList<Verb> skipVerbs = new ArrayList<Verb>();

	public CSVImporter() {
		skipVerbs.add(new Verb("doler"));
		skipVerbs.add(new Verb("llover"));
		skipVerbs.add(new Verb("nevar"));
		skipVerbs.add(new Verb("ocurrir"));
	}

	@Override
	public void loadVerbForms(String url, ArrayList<Verb> verbs) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
		String line = null;
		try {
			int lineNumber = 2;
			// Skip first line
			reader.readLine();
			String helper = "";
			while ((line = reader.readLine()) != null) {
				if (line != "") {
					// Progress for "spanisch verben.csv" In first line because of continue skipping
					if ((lineNumber % 1150) == 0){
						System.out.println((lineNumber / 11500.0) * 100 + "%");
						}
					lineNumber++;
					String[] split = line.split(";");
					// get infinitive
					Verb verb = new Verb(split[0]);
					// some verbs have strange forms, skip them, -> TODO
					// implementent in next version
					if (skipVerbs.contains(verb))
						continue;
					// only indicative, others in next version -> TODO
					if (!split[1].equals("Indicativo"))
						continue;
					// check if database already contains this verb, add if not
					if (verbs.contains(verb))
						verb = verbs.get(verbs.indexOf(verb));
					// check tense
					int temp = -1;
					String t = split[2];
					if (t.equals("Presente"))
						temp = Verb.PRESENT;
					else if (t.equals("Futuro"))
						temp = Verb.FUTURO;
					else if (t.equals("Imperfecto"))
						temp = Verb.IMPERFECTO;
					else if (t.equals("Pretérito"))
						temp = Verb.INDEFINIDO;
					// continue to next if illegal temp
					if (temp == -1)
						continue;
					// Special verbs like doler only have third person forms in
					// present
					if (split.length < 9) {
						verb.setVerbForm(3, temp, split[3]);
						verb.setVerbForm(6, temp, split[4]);
					} else
						for (int i = 3; i < 9; i++) {
							verb.setVerbForm(i - 2, temp, split[i]);
						}
					// get gerund and past participle not from every line (every
					// line with same infitive contais it)
					// if ((lineNumber % 10) == 0) {
					// if (split.length > 10) {
					if (!helper.equals(split[0])) {
						verb.setGerund(split[9]);
						verb.setParticiple(split[10]);
					}
					// }
					// }
					// helper for adding gerund/participle
					helper = verb.getInfinitve();
					// Add only if not already in db
					if (!verbs.contains(verb))
						verbs.add(verb);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(100 + "%");
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
