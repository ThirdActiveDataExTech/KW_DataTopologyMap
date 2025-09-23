package kware.apps.mobigen.dto.response.meta;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MetaValuesList {

    private String key;
    private List<String> values;
    private int total;

}
