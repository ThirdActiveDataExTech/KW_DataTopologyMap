package kware.apps.mobigen.cetus.dataset.dto.request;


import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMobigenPackageDataset {

    private CetusDatasetFile packageFile;
}
