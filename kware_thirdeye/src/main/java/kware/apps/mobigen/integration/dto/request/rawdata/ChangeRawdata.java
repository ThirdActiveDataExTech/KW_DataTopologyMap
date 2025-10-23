package kware.apps.mobigen.integration.dto.request.rawdata;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeRawdata {
    private String rawdataId;
    private String description;
    private List<String> tags;
}
