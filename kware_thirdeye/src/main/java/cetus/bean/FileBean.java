package cetus.bean;

import kware.common.file.domain.CommonFile;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class FileBean {

    private CommonFile[] fileAdd;
    private CommonFile[] fileDel;
}
