package mlssad.antipatterns.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import mlssad.antipatterns.detection.repository.TooMuchClusteringDetection;
import mlssad.kernel.impl.MLSAntiPattern;

public class TestCaseTooMuchClustering extends AbstractAntiPatternTestCase {
	String expectedAntiPattern = "TooMuchClustering";
	String expectedPackage = "antiPatternsJava.tooMuchClustering";
	String expectedClass = "TooMuchClustering";

	protected void setUp() throws Exception {
		super.setUp();
		detector = new TooMuchClusteringDetection();
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/TooMuchClustering/";
		expectedSmells = new HashSet<MLSAntiPattern>();
		List<String> nativeFunctions = Arrays.asList("version", "zmq_ctx_new", "zmq_ctx_set", "zmq_ctx_get",
				"zmq_ctx_destroy", "zmq_socket", "zmq_bind", "zmq_unbind", "zmq_connect", "zmq_disconnect", "zmq_close",
				"zmq_errno", "zmq_strerror", "zmq_send", "zmq_send", "zmq_recv", "zmq_recv", "zmq_recv",
				"zmq_setsockopt", "zmq_setsockopt", "zmq_setsockopt", "zmq_getsockopt_int", "zmq_getsockopt_long",
				"zmq_getsockopt_bytes", "zmq_poll", "zmq_z85_encode", "zmq_z85_decode", "zmq_curve_keypair");
		for (String nativeFunction : nativeFunctions)
			expectedSmells.add(new MLSAntiPattern(expectedAntiPattern, "", nativeFunction, expectedClass,
					expectedPackage, aPathJava));
	}
}
