package com.github.eltonsandre.cucumber.transformers;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.vavr.control.Try;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author eltonsandre
 */
@Slf4j
public class DefaultDataTransformer {

    private final ObjectMapper objectMapper = JsonMapper.builder()
                    .configure(MapperFeature.USE_ANNOTATIONS, false)
                    .findAndAddModules()
                    .build();

    @DefaultParameterTransformer
    public Object defaultParameterTransformer(final Object fromValue, final Type toValueType) {
        return Try.of(() -> this.to(fromValue, toValueType))
                .onFailure(e -> log.error("entry={}, type={}, exp.message={}", fromValue, toValueType, e.getMessage()))
                .getOrNull();
    }

    @DefaultDataTableEntryTransformer
    public Object transformer(final Map<String, String> fromValue, final Type toValueType) {
        return this.to(fromValue, toValueType);
    }

    @DefaultDataTableCellTransformer
    public Object to(final Object entry, final Type toValueType) {
        return Try.of(() -> this.objectMapper.convertValue(entry, (Class<?>) toValueType))
                .onFailure(e -> log.error("entry={}, type={}, exp.message={}", entry, toValueType, e.getMessage()))
                .getOrNull();
    }


}
