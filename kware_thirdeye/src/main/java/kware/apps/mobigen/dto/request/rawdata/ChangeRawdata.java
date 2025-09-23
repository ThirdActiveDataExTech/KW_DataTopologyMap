package kware.apps.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ChangeRawdata {

    private String description;
    private List<String> tags;

}
