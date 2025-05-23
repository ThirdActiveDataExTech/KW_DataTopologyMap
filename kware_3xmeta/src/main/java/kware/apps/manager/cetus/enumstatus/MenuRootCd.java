package kware.apps.manager.cetus.enumstatus;


import lombok.Getter;

@Getter
public enum MenuRootCd {

    TOP_ROOT("상단 메뉴 루트"),
    FOOTER_ROOT("푸터 메뉴 루트");

    private String description;

    MenuRootCd(String description) {
        this.description = description;
    }
}
