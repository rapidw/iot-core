package io.rapidw.iotcore.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class Function {
    private String id;
    private List<Field> fields;
}
