package kware.apps.system.workplace.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchWorkplace {

    private String browseText;

    public SearchWorkplace(String browseText) {
        this.browseText = browseText;
    }
}
