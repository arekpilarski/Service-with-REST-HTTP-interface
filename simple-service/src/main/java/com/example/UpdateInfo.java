package com.example;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class that is created in order to store information concerning an update.
 */

@XmlRootElement
public class UpdateInfo {

    private String operation;
    private int value;

    public String getOperation() {
        return operation;
    }
    public int getValue() {
        return value;
    }
}


