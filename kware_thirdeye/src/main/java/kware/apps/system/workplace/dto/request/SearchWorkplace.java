package kware.apps.system.workplace.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchWorkplace {

    private String browseText;
    private Long workplace;

    public SearchWorkplace(String browseText, Long workplace) {
        this.browseText = browseText;
        this.workplace = workplace;
    }
}
