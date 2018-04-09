package py4j.examples;
import py4j.GatewayServer;

public class ExampleFeatureEngineBasic {
    public static void main(String[] args) {
        GatewayServer.turnLoggingOff();
        GatewayServer server = new GatewayServer();
        server.start();
        IPythonExecutorBasic pe = (IPythonExecutorBasic) server.getPythonServerEntryPoint(new Class[] { IPythonExecutorBasic.class });
        try {
            //create monthly Inc Feature
            pe.compute_feature("{\"name\": \"monthlyInc\",\"input\": [\"annualInc\"],\"val\": \"return float(annualInc)/12.0\"}");
            //create a version of stability feature
            //pe.compute_feature("{\"name\": \"stability0\",\"input\": [\"empLength\"],\"val\":\"return float(empLength) * 10\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.shutdown();
    }
}
