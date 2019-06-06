package mlssad.kernel.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MLSAntiPattern {
	private String antiPatternName;

	private int lineNumber;
	private String variableName;
	private String methodName;
	private String className;
	private String packageName;
	private String filePath;

	public MLSAntiPattern(String aMethod, String aClass) {
		this.methodName = aMethod;
		this.className = aClass;
	}

	public MLSAntiPattern(String anAntiPattern, String aVariable, String aMethod, String aClass, String aPackage,
			String aPath) {
		this.antiPatternName = anAntiPattern;
		this.variableName = aVariable;
		this.methodName = aMethod;
		this.className = aClass;
		this.packageName = aPackage;
		this.filePath = aPath;
	}

	public String getAntiPatternName() {
		return antiPatternName;
	}

	public void setAntiPatternName(String antiPatternName) {
		this.antiPatternName = antiPatternName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	protected List<String> getElementsList() {
		return Arrays.asList(this.packageName, this.className, this.methodName, this.variableName);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (!(o instanceof MLSAntiPattern))
			return false;

		if (o == this)
			return true;

		return this.getElementsList().equals(((MLSAntiPattern) o).getElementsList());
	}

	@Override
	public int hashCode() {
		return methodName.hashCode() ^ className.hashCode();
	}

	@Override
	public String toString() {
		List<String> elements = this.getElementsList();
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = elements.iterator();
		boolean firstEl = true;

		while (it.hasNext()) {
			String el = it.next();
			if (el != null) {
				if (!firstEl) {
					sb.append(".");
				} else {
					firstEl = false;
				}
				sb.append(el);
			}
		}
		return sb.toString();
//		return String.format("%s.%s", className, methodName);
	}
}
