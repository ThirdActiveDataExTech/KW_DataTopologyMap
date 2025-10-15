package kware.apps.mobigen.cetus.dataset.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchMobigenDataset {

    private Long[] approvedIds;

    private Integer pageNumber;
    private Integer size;
}
