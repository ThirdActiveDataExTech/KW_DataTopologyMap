package kware.apps.mobigen.cetus.dataset.dto.response2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resPackage01 {
    private String code;
    private String message;
    private Package01_Result result;
    public resPackage01(String code, String message, Package01_Result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Package01_Result {
        private Long metadataId;
        private List<String> rawdataIds;

        public Package01_Result(Long metadataId, List<String> rawdataIds) {
            this.metadataId = metadataId;
            this.rawdataIds = rawdataIds;
        }
    }
}
