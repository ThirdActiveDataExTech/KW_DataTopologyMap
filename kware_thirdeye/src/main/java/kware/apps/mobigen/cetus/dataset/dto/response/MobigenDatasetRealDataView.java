package kware.apps.mobigen.cetus.dataset.dto.response;


import kware.common.file.domain.CommonFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobigenDatasetRealDataView {

    private String fileId;
    private String orgFileNm;
    private String filePath;
    private Long   fileSize;
    private String fileType;
    private String extension;
    private Integer downCnt;
    private String regDt;

}
