package kware.apps.system.group.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.system.group.domain.CetusGroup;
import kware.apps.system.group.domain.CetusGroupDao;
import kware.apps.system.group.dto.request.GroupMergeList;
import org.junit.jupiter.api.Test;

/**
 * 삭제 대상(uids)을 생략한 병합 요청은 NPE 없이 저장만 하고, 삭제 질의는 보내지 않는다.
 */
class CetusGroupServiceTest {

    private final CetusGroupDao dao = mock(CetusGroupDao.class);
    private final CetusGroupService service = new CetusGroupService(dao);

    @Test
    void 삭제_대상이_없으면_저장만_하고_삭제_질의는_없다() throws Exception {
        GroupMergeList request = new ObjectMapper().readValue(
                "{\"workplaceUid\":22,\"groups\":[{\"groupNm\":\"개발\",\"sortNo\":1},{\"uid\":5,\"groupNm\":\"기획\",\"sortNo\":2}]}", GroupMergeList.class);

        service.mergeGroup(request);

        verify(dao, times(1)).insert(any(CetusGroup.class));
        verify(dao, times(1)).update(any(CetusGroup.class));
        verify(dao, never()).updateUseAtToN(any(CetusGroup.class));
    }
}
