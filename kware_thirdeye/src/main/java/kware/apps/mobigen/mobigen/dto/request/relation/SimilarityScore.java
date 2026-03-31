package kware.apps.mobigen.mobigen.dto.request.relation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SimilarityScore {
    private float min;
    private float max;

    public SimilarityScore(float min, float max) {
        this.min = min;
        this.max = max;
    }
}
