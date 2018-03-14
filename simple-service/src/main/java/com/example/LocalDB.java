package com.example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * A class that has the role of local data resource created basing on provided .yml file.
 */
public class LocalDB {

    private List<EngineSensor> sensors;
    public String sourceUrl;

    public LocalDB() {
        try {
            sourceUrl = Main.SOURCE_URL;
            if(sourceUrl == null)  {

                // Creating Java Objects from YAML file located in the project.
                File yamlString = new File("./SourceFile/sensors.yml");
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                sensors = mapper.readValue(yamlString, new TypeReference<List<EngineSensor>>() {});
            } else {
                sourceUrl = createRawGithubLink(sourceUrl);
                URL url = new URL(sourceUrl);

                // Creating Java Objects from a file located on github.
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                sensors = mapper.readValue(url, new TypeReference<List<EngineSensor>>() {});
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method returning a link that allows reading from
     * a file located on github.
     *
     * @return Link to a file located on github.
     */

    public String createRawGithubLink(String string) {
        int startIndex = 0;
        for(int i = 0 ; i < 5 ; i++) {
            startIndex = string.indexOf("/",startIndex+1);
        }
         string = string.substring(0,startIndex) +
                  string.substring(startIndex+5,string.length());

        String regex = "\\s*\\bgithub\\b\\s*";
        return string.replaceFirst(regex, "raw.githubusercontent");
    }


    /**
     * Method returning list of all sensors.
     *
     * @return List of all sensors (EngineSensor objects).
     */

    public List<EngineSensor> getSensors() {
        return sensors;
    }


    /**
     * Method that searches through the local data resource
     * in order to find all engines which do not work properly.
     *
     * @param pressure pressure value under which an engine is claimed to not function well.
     * @param temperature temperature value over which an engine is claimed to not function well.
     * @return String that will be returned as a text/plain response.
     */
    public List<String> getBrokenEngines(String pressure, String temperature) {

        try {
            // Checking if both provided values are suitable for the program (of integer type).
            int pressure_threshold = Integer.parseInt(pressure);
            int temp_threshold = Integer.parseInt(temperature);

            // Creating a list for malfunctioning engines.
            List<String> brokenEngines = new ArrayList<>();

            // Declaring a temporarily stored sensor.
            EngineSensor temp;

            // Creating a list of malfunctioning engines (names) in three steps.
            for(EngineSensor s : sensors) {

                // First: Look for a pressure sensor that indicates too low value.
                if(s.getType().equals("pressure") && s.getValue() < pressure_threshold) {
                    temp = s;

                    // Second: Look for a temperature sensor of the same engine that indicates a too high value.
                    for(EngineSensor en : sensors) {
                        if(en.getType().equals("temperature") &&
                                en.getMaster_sensor_id().equals(temp.getId()) &&
                                en.getValue() > temp_threshold) {

                            // Third: Add such engine to the list of engines that do not work properly.
                            brokenEngines.add(temp.getEngine());
                            break;
                        }
                    }
                }
            }
            return brokenEngines;

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // Return an empty list if provided parameters are inappropriate.
        return new ArrayList<>();
    }


    /**
     * Method handling HTTP GET requests.
     * The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    public String updateSensor(String data, String id) {
        try {
            // Checking entered parameter.
            Integer.parseInt(id);

            // Creating a java object from passed data (JSON string).
            // Also checking if the data format is acceptable.
            ObjectMapper mapper = new ObjectMapper();
            UpdateInfo updateInfo = mapper.readValue(data, UpdateInfo.class);

            EngineSensor sensor = null;

            // Searching for a sensor of a given ID.
            for(EngineSensor s : sensors) {
                if(s.getId().equals(id)) {
                    sensor = s;
                    break;
                }
            }

            // Checking if the sensor has been found.
            if(sensor == null)
                return "\nSensor not found!\n";

            // Executing specified operation on the given sensor.
            switch(updateInfo.getOperation()) {
                case "increment":
                    sensor.setValue(sensor.getValue()+updateInfo.getValue());
                    break;
                case "set":
                    sensor.setValue(updateInfo.getValue());
                    break;
                case "decrement":
                    sensor.setValue(sensor.getValue() - updateInfo.getValue());
                    break;
                default:
                    return "\nInvalid operation type!\n";
            }


            // Checking if the set value is between sensor's boundary values and if not -
            // assigning the proper boundary value.
            if(sensor.getValue() > sensor.getMax_value())
                sensor.setValue(sensor.getMax_value());
            if(sensor.getValue() < sensor.getMin_value())
                sensor.setValue(sensor.getMin_value());

            return "\nSuccessfuly updated!\n";

        } catch (JsonParseException e) {
            return "\nUnsuitable data format!\n";
        } catch (JsonMappingException e) {
            return "\nIncorrect values found in data!\n";
        } catch (IOException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "\nIncorrect sensor ID!\n";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
