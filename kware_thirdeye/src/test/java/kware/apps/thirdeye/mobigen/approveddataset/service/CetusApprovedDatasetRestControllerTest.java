package kware.apps.thirdeye.mobigen.approveddataset.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchApprovedDataset;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 포털 목록 API 는 진열 노출(show_at = Y) 데이터셋만 조회해야 한다. 숨김 데이터셋은 관리자 목록에서만 보인다.
 */
class CetusApprovedDatasetRestControllerTest {

    private final CetusApprovedDatasetService service = mock(CetusApprovedDatasetService.class);
    private final CetusApprovedDatasetRestController controller = new CetusApprovedDatasetRestController(service);

    @Test
    void 포털_목록은_노출_데이터셋만_조회한다() {
        SearchApprovedDataset search = new SearchApprovedDataset(22L, 1L, null, null, null, "2025-01-01", "2026-01-01", "SBSI", null);

        controller.findDatasetList(search, null);

        ArgumentCaptor<SearchApprovedDataset> captor = ArgumentCaptor.forClass(SearchApprovedDataset.class);
        verify(service).findDatasetPage(captor.capture(), isNull());
        assertThat(captor.getValue().getShowAt()).isEqualTo("Y");
        assertThat(captor.getValue().getKeyword()).isEqualTo("SBSI");
    }

    @Test
    void 요청이_숨김을_지정해도_노출로_바꾼다() {
        SearchApprovedDataset search = new SearchApprovedDataset(22L, 1L, "N", null, null, null, null, null, null);

        controller.findDatasetList(search, null);

        ArgumentCaptor<SearchApprovedDataset> captor = ArgumentCaptor.forClass(SearchApprovedDataset.class);
        verify(service).findDatasetPage(captor.capture(), any());
        assertThat(captor.getValue().getShowAt()).isEqualTo("Y");
    }
}
