package kware.apps.manager.cetus.enumstatus;


import lombok.Getter;

@Getter
public enum DownloadTargetCd {

    CONTENTS("컨텐츠"),
    USER("유저");

    private String description;

    DownloadTargetCd(String description) {
        this.description = description;
    }
}
