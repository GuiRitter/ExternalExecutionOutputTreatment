package io.github.guiritter.external_execution_output_treatment;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Guilherme Alan Ritter
 */
@SuppressWarnings("CallToPrintStackTrace")
abstract class ReaderRunnable implements Runnable {

	private final BufferedReader reader;

	private String line;

	@Override
	public void run() {
		line = null;
		try {
			while ((line = reader.readLine()) != null) {
				treatLine(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	abstract void treatLine(String line);

	public ReaderRunnable(BufferedReader reader) {
		this.reader = reader;
	}
}
