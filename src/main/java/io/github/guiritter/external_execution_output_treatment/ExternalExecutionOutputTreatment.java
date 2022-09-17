package io.github.guiritter.external_execution_output_treatment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("CallToPrintStackTrace")
public abstract class ExternalExecutionOutputTreatment {

	final class ErrorReaderRunnable extends ReaderRunnable {

		@Override
		void treatLine(String line) {
			treatErrorLine(line);
		}

		public ErrorReaderRunnable(BufferedReader reader) {
			super(reader);
		}
	}

	final class InputReaderRunnable extends ReaderRunnable {

		@Override
		void treatLine(String line) {
			treatInputLine(line);
		}

		public InputReaderRunnable(BufferedReader reader) {
			super(reader);
		}
	}

	private Process process;

	public final void execute(String ...commands) throws IOException, InterruptedException {
		process = Runtime.getRuntime().exec(commands);
		new Thread(new ErrorReaderRunnable(new BufferedReader(
		 new InputStreamReader(process.getErrorStream())))).start();
		new Thread(new InputReaderRunnable(new BufferedReader(
		 new InputStreamReader(process.getInputStream())))).start();
		process.waitFor();
	}

	public abstract void treatErrorLine(String line);

	public abstract void treatInputLine(String line);

//	public static void main(String[] args) throws IOException, InterruptedException {
//		new ExternalExecutionOutputTreatment() {
//			@Override
//			public void treatErrorLine(String line) {
//				System.err.println(line);
//			}
//
//			@Override
//			public void treatInputLine(String line) {
//				System.out.println(line);
//			}
//		}.execute(new String[]{"wmctrl", "-a", "Terminal"});
//	}
}