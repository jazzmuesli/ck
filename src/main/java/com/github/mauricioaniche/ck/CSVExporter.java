package com.github.mauricioaniche.ck;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Export metrics into 4 CSV files.
 *  
 *
 */
public class CSVExporter {
	
	private static final Logger LOG = LoggerFactory.getLogger(CSVExporter.class);
	private static final String VARIABLE_FIELDS = "file,class,method,variable,usage";
	private static final String FIELD_FIELDS = "file,class,method,variable,usage";
	private static final String METHOD_FIELDS = "file,class,method,line,cbo,wmc,rfc,loc,returns,variables,parameters,startLine," +
			"loopQty,comparisonsQty,tryCatchQty,parenthesizedExpsQty,stringLiteralsQty,numbersQty,assignmentsQty," +
			"mathOperationsQty,maxNestedBlocks,anonymousClassesQty,subClassesQty,lambdasQty,uniqueWordsQty";
	private static final String CLASS_FIELDS = "file,class,type,cbo,wmc,dit,rfc,lcom,totalMethods,staticMethods,publicMethods,privateMethods,protectedMethods," +
			"defaultMethods,abstractMethods,finalMethods,synchronizedMethods,totalFields,staticFields,publicFields,privateFields," +
			"protectedFields,defaultFields,finalFields,synchronizedFields,nosi,loc,returnQty,loopQty,comparisonsQty," +
			"tryCatchQty,parenthesizedExpsQty,stringLiteralsQty,numbersQty,assignmentsQty,mathOperationsQty,variablesQty," +
			"maxNestedBlocks,anonymousClassesQty,subClassesQty,lambdasQty,uniqueWordsQty";
	public static final String classFileName = "class.csv";
	public static final String methodFileName = "method.csv";
	public static final String variableFileName = "variable.csv";
	public static final String fieldFileName = "field.csv";

	
	/**
	 * 
	 * @param path to analyse
	 * @param delim - better make it a non-comma because 
	 * generic signatures can contain a comma
	 * @param classFileName
	 * @param methodFileName
	 * @param variableFileName
	 * @param fieldFileName
	 * @throws FileNotFoundException
	 */
	public static void processDirectory(String path, String delim, String classFileName, String methodFileName,
			String variableFileName, String fieldFileName) throws FileNotFoundException {
		PrintStream classOutput = new PrintStream(classFileName);
		classOutput.println(CLASS_FIELDS.replaceAll(",", delim));

		PrintStream methodOutput = new PrintStream(methodFileName);
		methodOutput.println(METHOD_FIELDS.replaceAll(",", delim));

		PrintStream variableOutput = new PrintStream(variableFileName);
		variableOutput.println(VARIABLE_FIELDS.replaceAll(",", delim));

		PrintStream fieldOutput = new PrintStream(fieldFileName);
		fieldOutput.println(FIELD_FIELDS.replaceAll(",", delim));

		new CK().calculate(path, result -> {
			if(result.isError()) return;

			String cLine = result.getFile() + delim +
			result.getClassName() + delim +
			result.getType() + delim +
			result.getCbo() + delim +
			result.getWmc() + delim +
			result.getDit() + delim +
			result.getRfc() + delim +
			result.getLcom() + delim +
			result.getNumberOfMethods() + delim +
			result.getNumberOfStaticMethods() + delim +
			result.getNumberOfPublicMethods() + delim +
			result.getNumberOfPrivateMethods() + delim +
			result.getNumberOfProtectedMethods() + delim +
			result.getNumberOfDefaultMethods() + delim +
			result.getNumberOfAbstractMethods() + delim +
			result.getNumberOfFinalMethods() + delim +
			result.getNumberOfSynchronizedMethods() + delim +
			result.getNumberOfFields() + delim +
			result.getNumberOfStaticFields() + delim +
			result.getNumberOfPublicFields() + delim +
			result.getNumberOfPrivateFields() + delim +
			result.getNumberOfProtectedFields() + delim +
			result.getNumberOfDefaultFields() + delim +
			result.getNumberOfFinalFields() + delim +
			result.getNumberOfSynchronizedFields() + delim +
			result.getNosi() + delim +
			result.getLoc() + delim +
			result.getReturnQty() + delim +
			result.getLoopQty() + delim +
			result.getComparisonsQty() + delim +
			result.getTryCatchQty() + delim +
			result.getParenthesizedExpsQty() + delim +
			result.getStringLiteralsQty() + delim +
			result.getNumbersQty() + delim +
			result.getAssignmentsQty() + delim +
			result.getMathOperationsQty() + delim +
			result.getVariablesQty() + delim +
			result.getMaxNestedBlocks() + delim +
			result.getAnonymousClassesQty() + delim +
			result.getSubClassesQty() + delim +
			result.getLambdasQty() + delim +
			result.getUniqueWordsQty();
			int nFields = cLine.split(delim).length;
			if (nFields != 42) {
				String msg = "Unusual num of fields: " + nFields + " on line " + cLine;
				LOG.warn(msg);
				System.out.println(msg);
			}

			classOutput.println(
				cLine
			);

			for(CKMethodResult method : result.getMethods()) {
				String line = result.getFile() + delim +
				result.getClassName() + delim +
				method.getMethodName() + delim +
				method.getStartLine() + delim +
				method.getCbo() + delim +
				method.getWmc() + delim +
				method.getRfc() + delim +
				method.getLoc() + delim +
				method.getReturnQty() + delim +
				method.getVariablesQty() + delim +
				method.getParametersQty()  + delim +
				method.getStartLine() + delim +
				method.getLoopQty() + delim +
				method.getComparisonsQty() + delim +
				method.getTryCatchQty() + delim +
				method.getParenthesizedExpsQty() + delim +
				method.getStringLiteralsQty() + delim +
				method.getNumbersQty() + delim +
				method.getAssignmentsQty() + delim +
				method.getMathOperationsQty() + delim +
				method.getMaxNestedBlocks() + delim +
				method.getAnonymousClassesQty() + delim +
				method.getSubClassesQty() + delim +
				method.getLambdasQty() + delim +
				method.getUniqueWordsQty();
				nFields = line.split(delim).length;
				if (nFields != 25) {
					String msg = "Unusual num of fields: " + nFields + " on line " + line;
					System.out.println(msg);
					LOG.warn(msg);
				}
				methodOutput.println(line);

				for(Map.Entry<String,Integer> entry : method.getVariablesUsage().entrySet()) {

					variableOutput.println(
						result.getFile() + delim +
						result.getClassName() + delim +
						method.getMethodName() + delim +
						entry.getKey() + delim + entry.getValue());
				}

				for(Map.Entry<String,Integer> entry : method.getFieldUsage().entrySet()) {

					fieldOutput.println(
						result.getFile() + delim +
						result.getClassName() + delim +
						method.getMethodName() + delim +
						entry.getKey() + delim + entry.getValue());
				}
			}


		});
		
		classOutput.close();
		methodOutput.close();
		variableOutput.close();
		fieldOutput.close();
	}

}
