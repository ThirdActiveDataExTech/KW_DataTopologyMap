package kware.apps.system.group.service;


import cetus.user.UserUtil;
import kware.apps.system.group.domain.CetusGroupDao;
import kware.apps.system.group.dto.response.GroupList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusGroupService {

    private final CetusGroupDao dao;

    @Transactional(readOnly = true)
    public List<GroupList> findGroupList() {
        return dao.getGroupList(UserUtil.getUser().getWorkplaceUid());
    }
}
