package kware.apps.manager.cetus.form.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.manager.cetus.form.dto.request.ColumnsSearch;
import kware.apps.manager.cetus.form.dto.response.ColumnsPage;
import kware.apps.manager.cetus.form.dto.response.ColumnsView;
import org.springframework.stereotype.Component;

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

}
