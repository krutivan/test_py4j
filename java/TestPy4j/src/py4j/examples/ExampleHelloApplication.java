package py4j.examples;

import py4j.GatewayServer;

public class ExampleHelloApplication {
    public static void main(String[] args) {
        GatewayServer.turnLoggingOff();
        GatewayServer server = new GatewayServer();
        server.start();
        IHello hello = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });
        try {
            hello.sayHello();
            hello.sayHello(2, "Hello Again");
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.shutdown();
    }
}
