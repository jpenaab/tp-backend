/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author u$3R
 */
public class ReadProperties {

    private static String pathImagenes = "";

    private Properties prop = new Properties();

    static {

        new ReadProperties();

    }

    public ReadProperties() {

        try (InputStream input = new FileInputStream("tiempoplaya.properties")) {

            prop.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //set variables
        setPathImagenes(prop.getProperty("servidor.path.imagenes"));
    }

    public static String getPathImagenes() {
        return pathImagenes;
    }

    private void setPathImagenes(String pathImagenes) {
        this.pathImagenes = pathImagenes;
    } 
    
}
