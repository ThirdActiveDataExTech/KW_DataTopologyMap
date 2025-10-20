package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class reqPackage01 {
    private Long metadataId;
    private List<String> rawdataIds;
}
