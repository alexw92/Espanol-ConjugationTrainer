package dict;

import java.util.Comparator;
import java.util.HashMap;

public class VerbByFailureCountComparator implements Comparator<Verb> {

	HashMap<Verb, Integer> fails;

	public VerbByFailureCountComparator(LearnBox box) {
		this.fails = box.getFailures();
	}

	@Override
	public int compare(Verb a, Verb b) {
		if (fails.get(a) > fails.get(b))
			return -1;
		else if (fails.get(a) < fails.get(b))
			return 1;
		else
			return 0;
	}

}
