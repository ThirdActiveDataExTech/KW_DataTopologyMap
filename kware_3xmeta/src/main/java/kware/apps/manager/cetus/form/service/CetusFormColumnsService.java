package kware.apps.manager.cetus.form.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.form.domain.CetusColumnOptions;
import kware.apps.manager.cetus.form.domain.CetusColumnOptionsDao;
import kware.apps.manager.cetus.form.domain.CetusFormColumns;
import kware.apps.manager.cetus.form.domain.CetusFormColumnsDao;
import kware.apps.manager.cetus.form.dto.request.ColumnsChange;
import kware.apps.manager.cetus.form.dto.request.ColumnsSave;
import kware.apps.manager.cetus.form.dto.request.ColumnsSearch;
import kware.apps.manager.cetus.form.dto.response.ColumnsPage;
import kware.apps.manager.cetus.form.dto.response.ColumnsView;
import kware.apps.manager.cetus.form.dto.response.ElementType;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CetusFormColumnsService {

    private final CetusFormColumnsDao columnsDao;
    private final CetusColumnOptionsDao optionsDao;

    @Transactional
    public void save(ColumnsSave request) {
        CetusFormColumns formColumns = new CetusFormColumns(request);
        columnsDao.insert(formColumns);

        if (ElementType.from(request.getType()).requiresOption()) {
            if(request.getOptions() == null && request.getOptions().isEmpty()) {
                throw new SimpleException("옵션이 필요한 컬럼입니다.", HttpStatus.NO_CONTENT);
            }

            var options = request.getOptions();
            options.forEach(option -> {
                option.addColumnsUid(formColumns.getUid());
                option.addAuthor(formColumns.getRegUid());
                optionsDao.insert(option);
            });
        }

    }

    @Transactional
    public void change(Long uid, ColumnsChange request) {
        CetusFormColumns view = columnsDao.view(uid);
        columnsDao.update(view.changeColumns(uid, request));
    }

    @Transactional(readOnly = true)
    public Page<ColumnsPage> columns(ColumnsSearch request, Pageable pageable) {
        Page<ColumnsPage> page = columnsDao.page(request, pageable);

        List<ColumnsPage> list = page.getList();

        list.forEach(x -> {
            if(ElementType.from(x.getType()).requiresOption()) {
                List<CetusColumnOptions> options = optionsDao.list(x.getUid());
                x.addOptions(options);
            }
        });
        return page;
    }

    @Transactional(readOnly = true)
    public ColumnsView column(Long uid) {
        var data = columnsDao.findByUid(uid).orElseThrow();
        if(ElementType.from(data.getType()).requiresOption()) {
            List<CetusColumnOptions> options = optionsDao.list(data.getUid());
            data.addOptions(options);
        }
        return data;
    }

    @Transactional
    public void optionChange(Long uid, CetusColumnOptions request) {
        request.addUid(uid);
        optionsDao.update(request);
    }

}
