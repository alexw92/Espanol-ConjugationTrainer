package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import dict.Verb;

public class MyCSVImporter implements WordImporter {

    List<Charset> charsets = List.of(StandardCharsets.ISO_8859_1, StandardCharsets.UTF_16BE, StandardCharsets.UTF_16LE,
            StandardCharsets.UTF_8, StandardCharsets.UTF_16, StandardCharsets.US_ASCII);

    @Override
    public void loadVerbForms(String url, ArrayList<Verb> verbs) {

        //   String line = null;
            //   BufferedReader reader = Files.newBufferedReader(Paths.get(url), StandardCharsets.UTF_8);
            List<String> lines = null;
            for (Charset charset : charsets)
                try {
                    lines = Files.readAllLines(Paths.get(url), charset);
                    break;
                } catch (Exception ignored) {
                	ignored.printStackTrace();
                }
            int lineNumber = 2;
            //dont Skip first line, exported files dont have header
            //	reader.readLine();
            String helper = "";
            //  while ((line = reader.readLine()) != null) {
            for (String line : lines) {
                if (line != "") {
                    // Progress for "spanisch verben.csv" In first line because
                    // of continue skipping
                    if ((lineNumber % 1150) == 0) {
                        System.out.println((lineNumber / 11500.0) * 100 + "%");
                    }
                    // true if meaning entry
                    boolean meaning = false;
                    lineNumber++;
                    String[] split = line.split(";");
                    // get infinitive
                    Verb verb = new Verb(split[0]);
                    // some verbs have strange forms, skip them, -> TODO
                    // only indicative, others in next version -> TODO
                    // check if database already contains this verb, add if not
                    if (verbs.contains(verb))
                        verb = verbs.get(verbs.indexOf(verb));

                    if (split[1].equals("Indicativo")) {
                        // check tense
                        int temp = -1;
                        String t = split[2];
                        if (t.equals("Presente"))
                            temp = Verb.PRESENT;
                        else if (t.equals("Futuro"))
                            temp = Verb.FUTURO;
                        else if (t.equals("Imperfecto"))
                            temp = Verb.IMPERFECTO;
                        else if (t.equals("Indefinido"))
                            temp = Verb.INDEFINIDO;
                        else
                            System.out.println(t);
                        // set verb forms
                        for (int i = 3; i < 9; i++) {
                            verb.setVerbForm(i - 2, temp, split[i]);
                        }

                    } else if (split[1].equals("Gerundio")) {
                        verb.setGerund(split[2]);
                    } else if (split[1].equals("Participio")) {
                        verb.setParticiple(split[2]);
                    } else if (split[1].equals("Significados")) {
                        if (split.length > 2) {
                            String[] meanings = split[2].split(",");
                            for (String m : meanings) {
                                verb.addMeaning(m);
                            }
                        }
                    }

                    // }
                    // }
                    // helper for adding gerund/participle
                    helper = verb.getInfinitve();
                    // Add only if not already in db
                    if (!verbs.contains(verb))
                        verbs.add(verb);
                } else
                    continue;
            }

        System.out.println(100 + "%");
    }

}
