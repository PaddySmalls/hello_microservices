package de.stuttgart.hdm.csm.pk070.weather;

import com.sun.jersey.core.impl.provider.entity.ByteArrayProvider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author patrick.kleindienst
 */
public class WeatherDataMessageBodyReader implements MessageBodyReader<WeatherData> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.equals(WeatherData.class);
    }

    @Override
    public WeatherData readFrom(Class<WeatherData> type, Type genericType, Annotation[] annotations, MediaType
            mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException,
            WebApplicationException {

//        ByteArrayProvider

        return null;
    }
}
