package dict;

import java.util.Comparator;

public class VerbComparator implements Comparator<Verb> {

	@Override
	public int compare(Verb a, Verb b) {
		return a.getInfinitve().compareTo(b.getInfinitve());
	}

}
