package kware.apps.mobigen.integration.dto.request.pckg;

import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     SavePackageDataset
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      [PACKAGE_02] 패키지 데이터셋 업로드
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavePackageDataset {
    private CetusDatasetFile packageFile;
}
