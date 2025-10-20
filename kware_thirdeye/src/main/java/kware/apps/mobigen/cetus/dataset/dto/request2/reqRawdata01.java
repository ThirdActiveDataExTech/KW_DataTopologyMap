package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqRawdata01 {

    private final String action = "list";
    private String metadata_id;
    private reqRawdata01_pagination pagination;

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRawdata01_pagination {
        private int page;
        private int limit;
    }
}
