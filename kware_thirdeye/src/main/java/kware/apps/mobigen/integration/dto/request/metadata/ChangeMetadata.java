package kware.apps.mobigen.integration.dto.request.metadata;

import kware.apps.mobigen.cetus.tag.dto.request.SaveTag;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
* @fileName     ChangeMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      [METADATA_05] 메타데이터 정보 수정 요청 DTO
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMetadata {
    private Long datasetId;
    private String title;
    private String metadata;
    private CetusDatasetFile realFile;
    private List<SaveTag> tags;
}
