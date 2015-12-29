package dict;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * A LearnBox is a subset of dictionary verbs, which the user can use to create
 * his own learn portion Errors that the user makes during learning with this
 * words are counted with reference to the word. Learning Boxes don't save verbs
 * by themselves, but depend always on an underlying dictionary.
 * 
 * @author Alexander Werthmann
 *
 */
public class LearnBox {

	private ArrayList<Verb> verbs;
	private HashMap<Verb, Integer> failures;
	private String name;
	private Dictionary dict;
	private VerbByFailureCountComparator failureComp;
	//helper vars for frequent error learning
	private ArrayList<Verb> orderedByFails;
	private ArrayList<Verb> helper;
	private Random ran = new Random();

	public LearnBox(String name, Dictionary dict) {
		verbs = new ArrayList<Verb>();
		failures = new HashMap<Verb, Integer>();
		this.name = name;
		this.dict = dict;
		this.failureComp = new VerbByFailureCountComparator(this);
	}

	/**
	 * This method should be invoked before a sequence of invocations of
	 * getFrequentWrongVerb() Adding this code to every invocation of
	 * getFrequentWrongVerb() would cause too much overhead
	 */
	public void refreshFrequentWrongVerbList() {
		orderedByFails = new ArrayList<Verb>();
		helper = new ArrayList<Verb>();
		for(Verb v: verbs){
			if(failures.get(v)!=0)
				orderedByFails.add(v);
		}
		orderedByFails.sort(failureComp);
		
		for(Verb v : orderedByFails){
			for(int i=0;i<failures.get(v);i++){
				helper.add(v);
			}
		}
		
	}

	public Verb getFrequentWrongVerb() {
		
		int i = ran.nextInt(helper.size());
		return helper.get(i);
	}

	public void addVerb(Verb v) {
		if (!verbs.contains(v)) {
			verbs.add(v);
			failures.put(v, 0);
		} else
			System.out.println("Verb \"" + v.getInfinitve() + "\" is already in Learnbox \"" + name + "\".");
	}

	public void removeVerb(Verb v) {
		if (verbs.contains(v)) {
			verbs.remove(v);
			failures.remove(v);
		} else
			System.out.println("Verb \"" + v.getInfinitve() + "\" is not in Learnbox \"" + name + "\".");
	}

	public void addErrorCount(Verb v) {
		if (!verbs.contains(v))
			System.out.println("Verb \"" + v.getInfinitve() + "\" is not in Learnbox \"" + name + "\".");
		int failues = failures.get(v);
		failures.put(v, failues + 1);
	}

	public void setErrorCount(Verb v, int count) {
		if (!verbs.contains(v))
			System.out.println("Verb \"" + v.getInfinitve() + "\" is not in Learnbox \"" + name + "\".");
		failures.put(v, count);
	}

	/**
	 * Format of Files: atacar,0 beber,3 ... (Number is count of wrong answers
	 * given by the user being questioned for forms of this specific verb)
	 * 
	 * @param url
	 * @return
	 * @throws FileNotFoundException
	 */
	public static LearnBox loadLearnBox(String url, Dictionary dict) throws FileNotFoundException {
		BufferedReader reader = null;
		File f = new File(url);
		String name = f.getName();
		if (name.endsWith(".box"))
			name = name.substring(0, name.length() - 4);
		LearnBox box = new LearnBox(name, dict);
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String line = "";
		try {
			while (!((line = reader.readLine()) == null)) {
				String[] split = line.split(",");
				Verb v = null;
				int failures = 0;
				// Nur infinitiv ohne fehleranzahl
				if (split.length == 1 && line != "") {
					v = dict.getVerb(line);
					if (v == null) {
						System.out.println("Wort " + line + " konnte nicht im Wörterbuch gefunden werden.");
						continue;
					}
					box.addVerb(v);
					box.setErrorCount(v, 0);
				}
				// Infinitiv mit fehleranzahl
				else {
					String inf = split[0];
					v = dict.getVerb(inf);
					if (v == null) {
						System.out.println("Wort " + inf + " konnte nicht im Wörterbuch gefunden werden.");
						continue;
					}
					// System.out.println(line+" "+split.length);
					failures = Integer.parseInt(split[1]);
					box.addVerb(v);
					box.setErrorCount(v, failures);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return box;
	}

	public void saveLearnBox(String url) {
		if (url == null) {
			url = "LearnBoxes/" + name + ".box";
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(url)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Verb v : verbs) {
			int fail = failures.get(v);
			try {
				writer.write(v.getInfinitve() + ((fail == 0) ? "" : "," + fail + ""));
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Verb> getVerbs() {
		return verbs;
	}

	public void setVerbs(ArrayList<Verb> verbs) {
		this.verbs = verbs;
	}

	public HashMap<Verb, Integer> getFailures() {
		return failures;
	}

	public void setFailures(HashMap<Verb, Integer> failures) {
		this.failures = failures;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dictionary getDict() {
		return dict;
	}

	public void setDict(Dictionary dict) {
		this.dict = dict;
	}

	public String toString() {
		String res = "LernBox " + name + ":\n";
		if (verbs.isEmpty())
			res += "leer\n";
		else {
			res += verbs.size() + " Wörter\n";
			for (Verb v : verbs) {
				int f = failures.get(v);
				res += v.getInfinitve() + ((f == 0) ? "" : ("(" + f + ")")) + "\n";
			}
		}
		return res;
	}

}
