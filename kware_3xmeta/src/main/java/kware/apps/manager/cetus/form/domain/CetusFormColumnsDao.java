package kware.apps.manager.cetus.form.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.manager.cetus.form.dto.request.ColumnsSearch;
import kware.apps.manager.cetus.form.dto.response.ColumnsPage;
import kware.apps.manager.cetus.form.dto.response.ColumnsView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CetusFormColumnsDao extends SuperDao<CetusFormColumns> {

    public CetusFormColumnsDao() {
        super("cetusFormColumns");
    }

    public Page<ColumnsPage> page(ColumnsSearch bean, Pageable pageable) {
        return page("list", "count", bean , pageable);
    }

    public Optional<ColumnsView> findByUid(Long uid) {
        return Optional.of(selectOne("findByUid", uid));
    }

    public Integer findNextSortNum(@Param("formGroup") String formGroup) {
        return selectOne("findNextSortNum", formGroup);
    }

    public List<ColumnsPage> findByTenanyAndFormGroup(ColumnsSearch bean) {
        return selectList("findByTenanyAndFormGroup", bean);
    }

    public void deleteColumns(@Param("uid") Long uid) {
        update("deleteColumns", uid);
    }


    public Integer existFieldName(String formGroup, String fieldName, Long workplaceUid) {
        Map<String, Object> param = new HashMap<>();
        param.put("formGroup", formGroup);
        param.put("name", fieldName);
        param.put("workplaceUid", workplaceUid);

        return selectOne("existFieldName", param);
    }

}


