package kware.apps.thirdeye.enumstatus;

import lombok.Getter;

@Getter
public enum DatasetTargetCd {

    MOBIGEN("모비젠"),
    KWARE("케이웨어");

    private String koName;

    DatasetTargetCd(String koName) {
        this.koName = koName;
    }
}
