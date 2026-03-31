package kware.apps.mobigen.integration.dto.response.metadata;

import kware.apps.mobigen.mobigen.dto.response.metadata.ViewMetadataResponse;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class MetadataView {

    private String metadataId;                    // 데이터셋 ID

    // from. mobigen
    private ViewMetadataResponse metadataResponse;      // 모비젠으로부터 얻어오는 데이터셋의 view 응답

    // from. kware
    private String registrantId;                        // 등록자 ID
    private CetusDatasetFileView packagedataFile;       // 패키지데이터 파일
    private CetusDatasetFileView metadataFile;          // 메타데이터 파일
    private List<CetusDatasetFileView> rawdataFiles;    // 원본데이터 파일 목록

    public MetadataView(ViewMetadataResponse metadataResponse, String metadataId) {
        this.metadataResponse = metadataResponse;
        this.metadataId = metadataId;
    }
}
