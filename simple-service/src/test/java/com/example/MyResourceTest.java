package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


    /**
     * Test to see if incorrect parameter exception causes return of an empty list.
     */
    @Test
    public void testBrokenEngineMethod() {
        LocalDB db = new LocalDB();

        // Unsuitable parameters
        assertEquals(0, db.getBrokenEngines("wrong1", "wrong2").size());
    }

    /**
     * Test to see if any incorrect data provided is spotted.
     */
    @Test
    public void testUpdateSensorMethod() {
        LocalDB db = new LocalDB();

        // Unsuitable data format entered
        assertEquals("\nUnsuitable data format!\n", db.updateSensor("{Not, Json, Structure)","10"));

        // Incorrect value provided
        assertEquals("\nIncorrect values found in data!\n", db.updateSensor( "{\"operation\":\"set\",\"value\":\"wrongtype\"}","10"));

        // Sensor ID is not an integer
        assertEquals("\nIncorrect sensor ID!\n", db.updateSensor( "{\"operation\":\"set\",\"value\":\"40\"}"
                ,"id"));
    }

    @Test
    public void testCreateRawGithubLinkMethod() {
        LocalDB db = new LocalDB();
        String input = "https://github.com/arekpilarski/Files/blob/master/sensors.yml";
        String output = "https://raw.githubusercontent.com/arekpilarski/Files/master/sensors.yml";
        assertEquals(output, db.createRawGithubLink(input));
    }






}
