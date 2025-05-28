package kware.apps.asp.contents.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HomeData {

    private String category;
    private String title;
    private String description;
    private String image;
    private String url;
}
