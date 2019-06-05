package com.github.mauricioaniche.ck;

import java.io.FileNotFoundException;

public class Runner {

	public static void main(String[] args) throws FileNotFoundException {

		if (args == null || args.length != 1) {
			System.out.println("Usage java -jar ck.jar <path to project>");
			System.exit(1);
		}

		String path = args[0];
		String delim = ",";

		CSVExporter.processDirectory(path, delim, CSVExporter.classFileName, CSVExporter.methodFileName,
				CSVExporter.variableFileName, CSVExporter.fieldFileName);
	}

}
