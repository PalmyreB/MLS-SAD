package mlssad.kernel.impl;

import java.util.Arrays;
import java.util.List;

public class MLSCodeSmell {

	private String codeSmellName;

	private int lineNumber;
	private String variableName;
	private String methodName;
	private String className;
	private String packageName;
	private String filePath;

	public MLSCodeSmell(String aMethod, String aClass) {
		this.methodName = aMethod;
		this.className = aClass;
	}

	public MLSCodeSmell(String aCodeSmell, String aVariable, String aMethod, String aClass, String aPackage,
			String aPath) {
		this.codeSmellName = aCodeSmell;
		this.variableName = aVariable;
		this.methodName = aMethod;
		this.className = aClass;
		this.packageName = aPackage;
		this.filePath = aPath;
	}

	public String getCodeSmellName() {
		return codeSmellName;
	}

	public void setAntiPatternName(String antiPatternName) {
		this.codeSmellName = antiPatternName;
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

		if (!(o instanceof MLSCodeSmell))
			return false;

		if (o == this)
			return true;

		return this.getElementsList().equals(((MLSCodeSmell) o).getElementsList());
	}

	@Override
	public int hashCode() {
		return this.getElementsList().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Code Smell");
		if (!variableName.isEmpty())
			sb.append("\n\tVariable: " + variableName);
		if (!methodName.isEmpty())
			sb.append("\n\tMethod: " + methodName);
		if (!className.isEmpty())
			sb.append("\n\tClass: " + className);
		if (!packageName.isEmpty())
			sb.append("\n\tPackage: " + packageName);
		sb.append("\n}");
		return sb.toString();
	}

}
