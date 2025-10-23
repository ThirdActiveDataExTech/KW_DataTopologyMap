package kware.apps.mobigen.integration.dto.request.rawdata;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteRawdatas {
    private Long metadataId;
    private List<String> rawdataIds;
}
