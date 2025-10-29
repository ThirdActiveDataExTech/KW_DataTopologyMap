package kware.apps.mobigen.integration.dto.request.metadata;

import kware.apps.mobigen.cetus.tag.dto.request.SaveTag;
import kware.apps.thirdeye.mobigen.datasetfile.domain.file.CetusDatasetFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
* @fileName     SaveMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      [METADATA_03] 메타데이터 생성
 *              (+) 원본데이터 파일 업로드  [선택]
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMetadata {
    private String title;
    private String extdata;
    private CetusDatasetFile metaFile;
    private CetusDatasetFile realFile;
    private List<SaveTag> tags;
}
