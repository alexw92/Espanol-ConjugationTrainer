package learn.conjugation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface State {

	public void execute(BufferedReader in, BufferedWriter out ) throws IOException;
	
}
