package mlssad.kernel.impl;

public class MLSAntiPattern {
	private String apMethod;
	private String apClass;

	public MLSAntiPattern(String aMethod, String aClass) {
		this.setApMethod(aMethod);
		this.apClass = aClass;
	}

	public String getApMethod() {
		return apMethod;
	}

	public void setApMethod(String apMethod) {
		this.apMethod = apMethod;
	}

	public String getApClass() {
		return apClass;
	}

	public void setApClass(String apClass) {
		this.apClass = apClass;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (!(o instanceof MLSAntiPattern))
			return false;

		if (o == this)
			return true;

		return this.apMethod.equals(((MLSAntiPattern) o).getApMethod())
				&& this.apClass.equals(((MLSAntiPattern) o).getApClass());
	}

	@Override
	public int hashCode() {
		return apMethod.hashCode() ^ apClass.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s.%s", apClass, apMethod);
	}
}
