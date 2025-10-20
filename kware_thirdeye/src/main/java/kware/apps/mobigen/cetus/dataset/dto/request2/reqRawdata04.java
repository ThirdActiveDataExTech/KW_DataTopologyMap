package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter  @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqRawdata04 {

    private final String action = "detail";
    private String metadata_id;
    private String rawdata_id;
}
