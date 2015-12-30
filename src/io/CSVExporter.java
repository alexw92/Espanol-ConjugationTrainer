package io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import dict.Verb;
import dict.VerbComparator;

public class CSVExporter implements WordExporter {

	@Override
	public void saveVerbForms(String url, ArrayList<Verb> verbs) throws FileNotFoundException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(url)));
		VerbComparator comp = new VerbComparator();
		verbs.sort(comp);
		for (Verb v : verbs) {
			// write line for present
			String pre = v.getInfinitve() + ";Indicativo;" + "Presente;" + v.getVerbForm(1, false, Verb.PRESENT) + ";"
					+ v.getVerbForm(2, false, Verb.PRESENT) + ";" + v.getVerbForm(3, false, Verb.PRESENT) + ";"
					+ v.getVerbForm(1, true, Verb.PRESENT) + ";" + v.getVerbForm(2, true, Verb.PRESENT) + ";"
					+ v.getVerbForm(3, true, Verb.PRESENT);
			// write line for imperfecto
			String imp = v.getInfinitve() + ";Indicativo;" + "Imperfecto;" + v.getVerbForm(1, false, Verb.IMPERFECTO)
					+ ";" + v.getVerbForm(2, false, Verb.IMPERFECTO) + ";" + v.getVerbForm(3, false, Verb.IMPERFECTO)
					+ ";" + v.getVerbForm(1, true, Verb.IMPERFECTO) + ";" + v.getVerbForm(2, true, Verb.IMPERFECTO)
					+ ";" + v.getVerbForm(3, true, Verb.IMPERFECTO);
			// write line for indefinido
			String ind = v.getInfinitve() + ";Indicativo;" + "Indefinido;" + v.getVerbForm(1, false, Verb.INDEFINIDO)
					+ ";" + v.getVerbForm(2, false, Verb.INDEFINIDO) + ";" + v.getVerbForm(3, false, Verb.INDEFINIDO)
					+ ";" + v.getVerbForm(1, true, Verb.INDEFINIDO) + ";" + v.getVerbForm(2, true, Verb.INDEFINIDO)
					+ ";" + v.getVerbForm(3, true, Verb.INDEFINIDO);
			// write line for futuro
			String fut = v.getInfinitve() + ";Indicativo;" + "Futuro;" + v.getVerbForm(1, false, Verb.FUTURO) + ";"
					+ v.getVerbForm(2, false, Verb.FUTURO) + ";" + v.getVerbForm(3, false, Verb.FUTURO) + ";"
					+ v.getVerbForm(1, true, Verb.FUTURO) + ";" + v.getVerbForm(2, true, Verb.FUTURO) + ";"
					+ v.getVerbForm(3, true, Verb.FUTURO);
			// write line for gerund
			String gerund = v.getInfinitve() + ";Gerundio;" + v.getGerund();
			// write line for participio
			String participio = v.getInfinitve() + ";Participio;" + v.getParticiple();
			// Write line for meanings
			String meanings = v.getInfinitve()+";Significados;";
			if (!v.getMeanings().isEmpty()) {
				for (int i = 0; i < v.getMeanings().size(); i++) {
					String m = v.getMeanings().get(i);
					// if not last meaning
					if (i != v.getMeanings().size() - 1)
						meanings += m + ",";
					else
						meanings += m;
				}
			}
			// Write data into File
			try {
				out.write(pre);
				out.newLine();
				out.flush();
				out.write(imp);
				out.newLine();
				out.flush();
				out.write(ind);
				out.newLine();
				out.flush();
				out.write(fut);
				out.newLine();
				out.flush();
				out.write(gerund);
				out.newLine();
				out.flush();
				out.write(participio);
				out.newLine();
				out.flush();
				//not all verbs have a meaning entry
				if (meanings != "") {
					out.write(meanings);
					out.newLine();
					out.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
