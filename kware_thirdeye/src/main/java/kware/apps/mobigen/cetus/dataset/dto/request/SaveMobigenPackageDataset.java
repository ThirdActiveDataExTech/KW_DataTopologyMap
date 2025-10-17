package kware.apps.mobigen.cetus.dataset.dto.request;


import kware.common.file.domain.CommonFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMobigenPackageDataset {

    private CommonFile[] packageFile;
}
