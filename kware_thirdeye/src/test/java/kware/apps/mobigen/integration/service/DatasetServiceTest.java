package kware.apps.mobigen.integration.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.mobigen.integration.dto.request.metadata.DeleteMetadatas;
import kware.apps.mobigen.mobigen.dto.request.metadata.DeleteMetadatasRequest;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.ApiStatus;
import kware.apps.mobigen.mobigen.dto.response.metadata.DeleteMetadataResponse;
import kware.apps.mobigen.mobigen.service.MobigenExternalApiService;
import kware.apps.thirdeye.bookmark.service.CetusBookMarkService2;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService2;
import kware.apps.thirdeye.mobigen.datasetfile.service.CetusDatasetFileService;
import kware.apps.thirdeye.mobigen.thirdeyeregistrant.service.CetusThirdeyeRegistrantService;
import kware.common.exception.SimpleException;
import org.junit.jupiter.api.Test;

/**
 * 메타데이터 여러 건 삭제: 외부 삭제가 성공하면 북마크·진열·파일 정보는 ID 목록으로 한 번씩만 지우고,
 * 외부 삭제가 실패하면 포탈 쪽 데이터는 건드리지 않는다.
 */
class DatasetServiceTest {

    private final MobigenExternalApiService apiService = mock(MobigenExternalApiService.class);
    private final CetusDatasetFileService datasetFileService = mock(CetusDatasetFileService.class);
    private final CetusBookMarkService2 bookMarkService2 = mock(CetusBookMarkService2.class);
    private final CetusMobigenDatasetService mobigenDatasetService = mock(CetusMobigenDatasetService.class);
    private final DatasetService service = new DatasetService(apiService, datasetFileService,
            mock(CetusThirdeyeRegistrantService.class), mock(CetusApprovedDatasetService2.class), bookMarkService2, mobigenDatasetService);

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 삭제_성공_시_부가_정보는_ID_목록으로_각_한_번씩만_지운다() throws Exception {
        List<String> ids = Arrays.asList("M-1", "M-2", "M-3");
        when(apiService.deleteSeveralMetadata(any(DeleteMetadatasRequest.class))).thenReturn(response(ApiStatus.SUCCESS));

        service.deleteMetadatas(mapper.readValue("{\"uids\":[\"M-1\",\"M-2\",\"M-3\"]}", DeleteMetadatas.class));

        verify(bookMarkService2, times(1)).deleteBookMarkByMetadataIds(ids);
        verify(mobigenDatasetService, times(1)).ifExistDeleteApprovedDatasets(ids);
        verify(datasetFileService, times(1)).processDelMetadataFiles(ids);
        verify(datasetFileService, never()).processDelFile(any());
    }

    @Test
    void 외부_삭제가_실패하면_예외를_내고_포탈_데이터는_건드리지_않는다() throws Exception {
        when(apiService.deleteSeveralMetadata(any(DeleteMetadatasRequest.class))).thenReturn(response(ApiStatus.ERROR));

        assertThatThrownBy(() -> service.deleteMetadatas(mapper.readValue("{\"uids\":[\"M-1\"]}", DeleteMetadatas.class)))
                .isInstanceOf(SimpleException.class);

        verify(bookMarkService2, never()).deleteBookMarkByMetadataIds(anyList());
        verify(mobigenDatasetService, never()).ifExistDeleteApprovedDatasets(anyList());
        verify(datasetFileService, never()).processDelMetadataFiles(anyList());
    }

    private static ApiResponse<DeleteMetadataResponse> response(ApiStatus status) {
        ApiResponse<DeleteMetadataResponse> response = new ApiResponse<>();
        response.setCode(status.getCode());
        response.setMessage(status.name());
        return response;
    }
}
