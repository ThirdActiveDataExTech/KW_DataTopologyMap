package kware.apps.system.group.service;


import cetus.user.UserUtil;
import kware.apps.system.group.domain.CetusGroup;
import kware.apps.system.group.domain.CetusGroupDao;
import kware.apps.system.group.dto.request.GroupMerge;
import kware.apps.system.group.dto.request.GroupMergeList;
import kware.apps.system.group.dto.response.GroupList;
import kware.apps.system.group.dto.response.GroupListWithUser;
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

    @Transactional(readOnly = true)
    public List<GroupListWithUser> findGroupListByWorkplaceUid(Long workplceUid) {
        return dao.getGroupListByWorkplaceUid(workplceUid);
    }

    public void mergeGroup(GroupMergeList request) {
        for (GroupMerge group: request.getGroups()) {
            CetusGroup bean = new CetusGroup(request.getWorkplaceUid(), group);
            if(group.getUid() == null) dao.insert(bean);
            else dao.update(bean);
        }
        if(request.getUids().length != 0) {
            for(Long uid : request.getUids()) {
                CetusGroup bean = new CetusGroup(uid);
                dao.updateUseAtToN(bean);
            }
        }
    }
}
