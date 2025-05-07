package kware.apps.manager.cetus.menu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kware.apps.manager.cetus.menu.dto.response.MenuList;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CetusMenu implements Serializable {
    @JsonIgnore
    private final Long menuNo;
    private final String menuNm;
    private final String url;
    private final int sortNo;
    private final String menuIcon;
    private final List<CetusMenu> children = new ArrayList<>();

    public CetusMenu(MenuList menu) {

        this.menuNo = menu.getMenuNo();
        this.menuNm = menu.getMenuNm();
        this.menuIcon = menu.getMenuIcon();
        if (menu.getUrl() != null) {
            this.url = menu.getUrl().equals("/") ? "#" : menu.getUrl();
        } else {
            this.url = "#";
        }

        this.sortNo = menu.getSortNo();
    }

    public boolean hasChild() {
        return !children.isEmpty();
    }
}
