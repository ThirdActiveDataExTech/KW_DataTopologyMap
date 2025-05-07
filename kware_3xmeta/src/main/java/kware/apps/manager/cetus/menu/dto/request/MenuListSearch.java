package kware.apps.manager.cetus.menu.dto.request;

import cetus.annotation.YOrN;
import lombok.Getter;


@Getter
public class MenuListSearch {

    @YOrN
    private String useAt;

    public MenuListSearch(String useAt) {
        this.useAt = useAt;
    }
}
