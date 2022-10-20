package ecomerce.sales.tools;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.vladmihalcea.hibernate.type.util.ObjectMapperSupplier;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.function.Function;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Configuration
public class JsonConfiguration implements ObjectMapperSupplier {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .registerModule(new CustomModule())
            .setVisibility(PropertyAccessor.CREATOR, ANY)
            .setVisibility(PropertyAccessor.FIELD, ANY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, true)
            .configure(MapperFeature.AUTO_DETECT_FIELDS, true)
            .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false)
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

    @Bean
    ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        return new MappingJackson2HttpMessageConverter(OBJECT_MAPPER);
    }

    public static <T> T parse(String payloadAsJson, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(payloadAsJson, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(String payloadAsJson, Class<T> type, T defaultValue) {
        try {
            return OBJECT_MAPPER.readValue(payloadAsJson, type);
        } catch (JsonProcessingException e) {
            return defaultValue;
        }
    }

    public static String json(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * used for hibernate's object as json serialisation
     */
    @Override
    public ObjectMapper get() {
        return OBJECT_MAPPER;
    }

    private static class CustomModule extends SimpleModule {
        public CustomModule() {
            //addSerializer(CartId.class, new SerializerToString<>(CartId::id));
            //addDeserializer(CartId.class, new DeserializerFromString<>(CartId::of));
        }
    }

    @AllArgsConstructor
    private static class SerializerToString<T> extends JsonSerializer<T> {
        private final Function<T, String> fun;

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(fun.apply(value));
        }
    }

    @AllArgsConstructor
    private static class DeserializerFromString<T> extends JsonDeserializer<T> {
        private final Function<String, T> fun;

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return fun.apply(p.readValueAs(String.class));
        }
    }
}
