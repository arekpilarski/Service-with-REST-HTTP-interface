package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Root resource (exposed at "resource" path).
 */
@Singleton
@Path("resource")
public class MyResource {

    // Declaration of LocalDB (local data resource).
    LocalDB db;

    // Constructor that creates local database(data resource)
    // based on a .yml file.
    public MyResource () {
        db = new LocalDB();
    }

    /**
     * Method handling HTTP GET requests.
     * The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Server running.";
    }


    /**
     * Method handling HTTP GET requests.
     * Returns all sensors as a "application/json" media type.
     *
     * @return List of all sensors as a application/json media type.
     */

    @GET
    @Path("sensors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EngineSensor> getSensors() {
        return db.getSensors();
    }

    /**
     * Method handling HTTP GET requests.
     * Takes two query parameters (pressure and temperature
     * thresholds) in order to find malfunctioning engines.
     *
     * @return List of engines that do not work properly.
     */

    @GET
    @Path("engines")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getBrokenEngines(
            @QueryParam("pressure_threshold") String pressure,
            @QueryParam("temp_threshold") String temperature) {

        return db.getBrokenEngines(pressure, temperature);
    }


    /**
     * Method handling HTTP POST requests.
     * Updates a sensor specified by id, changing its value
     * depending on operation type (increment, set, decrement).
     *
     * @return String message informing about the result of update.
     */

    @POST
    @Path("sensors/{id}")
    public String updateSensor(String data, @PathParam("id") String id) {
        return db.updateSensor(data, id);
    }
}
