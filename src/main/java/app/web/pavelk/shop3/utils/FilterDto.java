package app.web.pavelk.shop3.utils;

import lombok.Data;

import java.util.List;

@Data
public class FilterDto {
    String name;
    String min;
    String max;
    List<String> set;
}
