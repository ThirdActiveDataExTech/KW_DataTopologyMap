package kware.apps.mobigen.cetus.dataset.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchMobigenDataset {

    private Long[] approvedIds;
    private int offset;
    private int limit;

    public SearchMobigenDataset(int pageNumber, int size) {
        this.offset = (pageNumber - 1) * size;
        this.limit = size;
    }
}
