package mlssad.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

final public class PropertyGetter {

	public static int getIntProp(String prop, int defaultValue) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("../MLS SAD/rsc/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Integer.parseInt(props.getProperty(prop, Integer.toString(defaultValue)));
	}

}
