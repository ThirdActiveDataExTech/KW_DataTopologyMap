package kware.apps.mobigen.cetus.dataset.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SearchMobigenDataset {

    private String registrantId;
    private Long[] approvedIds;
}
