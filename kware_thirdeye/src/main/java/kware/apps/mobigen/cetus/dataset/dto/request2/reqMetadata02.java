package kware.apps.mobigen.cetus.dataset.dto.request2;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqMetadata02 {

    private final String action = "delete";
    private List<String> metadata_ids;
}
