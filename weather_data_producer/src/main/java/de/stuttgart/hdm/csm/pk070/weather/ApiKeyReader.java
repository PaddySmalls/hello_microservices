package de.stuttgart.hdm.csm.pk070.weather;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by patrick on 05.11.16.
 */

@Component
public class ApiKeyReader {

    private static final String DEFAULT_KEY_PATH = "/tmp/api_key";

    public String readKey() {
        BufferedReader reader = null;
        String apiKey = null;

        try {
            reader = new BufferedReader(new FileReader(DEFAULT_KEY_PATH));
            apiKey = reader.readLine();

            if (Objects.isNull(apiKey) || apiKey.equals("")) {
                throw new IllegalArgumentException("API key must not be empty!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(reader)) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return apiKey;
    }
}
