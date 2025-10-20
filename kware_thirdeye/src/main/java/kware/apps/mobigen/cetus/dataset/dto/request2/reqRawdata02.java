package kware.apps.mobigen.cetus.dataset.dto.request2;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqRawdata02 {
    private final String action = "create";
    private String metadata_id;
    private String rawdata_format;
}
