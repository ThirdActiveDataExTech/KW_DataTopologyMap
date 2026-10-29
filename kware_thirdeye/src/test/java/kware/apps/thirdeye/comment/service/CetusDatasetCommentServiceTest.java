package kware.apps.thirdeye.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.comment.domain.CetusDatasetComment;
import kware.apps.thirdeye.comment.domain.CetusDatasetCommentDao;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSave;
import kware.common.exception.SimpleException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;

/**
 * 의견 유형 코드는 사용 중인 DATASET_COMMENT 코드만 저장한다. 아닌 값은 400 으로 거절하고 저장하지 않는다.
 */
class CetusDatasetCommentServiceTest {

    private final CetusDatasetCommentDao dao = mock(CetusDatasetCommentDao.class);
    private final CetusDatasetCommentService service = new CetusDatasetCommentService(dao);

    @Test
    void 허용된_유형이면_저장한다() throws Exception {
        when(dao.countCommentType("COMMENT_OPINION")).thenReturn(1);
        DatasetCommentSave request = request("COMMENT_OPINION");

        service.saveDatasetComment(request);

        ArgumentCaptor<CetusDatasetComment> saved = ArgumentCaptor.forClass(CetusDatasetComment.class);
        verify(dao).insert(saved.capture());
        assertThat(saved.getValue().getApprovedDatasetUid()).isEqualTo(180L);
        assertThat(saved.getValue().getTypeCd()).isEqualTo("COMMENT_OPINION");
        assertThat(saved.getValue().getRatings()).isEqualTo(4);
    }

    @Test
    void 없는_유형이면_400_으로_거절하고_저장하지_않는다() throws Exception {
        when(dao.countCommentType("HACK")).thenReturn(0);

        assertThatThrownBy(() -> service.saveDatasetComment(request("HACK")))
                .isInstanceOf(SimpleException.class)
                .satisfies(e -> assertThat(((SimpleException) e).getStatus()).isEqualTo(HttpStatus.BAD_REQUEST));
        verify(dao, never()).insert(any(CetusDatasetComment.class));
    }

    private static DatasetCommentSave request(String typeCd) throws Exception {
        return new ObjectMapper().readValue(
                "{\"approvedUid\":180,\"typeCd\":\"" + typeCd + "\",\"ratings\":4,\"comment\":\"의견\"}", DatasetCommentSave.class);
    }
}
