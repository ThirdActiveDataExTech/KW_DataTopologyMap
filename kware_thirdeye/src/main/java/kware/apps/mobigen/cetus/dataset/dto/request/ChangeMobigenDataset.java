package kware.apps.mobigen.cetus.dataset.dto.request;


import kware.apps.mobigen.cetus.tag.dto.request.SaveTag;
import kware.apps.thirdeye.mobigen.datasetfile.domain.CetusDatasetFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMobigenDataset {

    private String title;
    private String metadata;
    private CetusDatasetFile realFile;
    private List<SaveTag> tags;

}
