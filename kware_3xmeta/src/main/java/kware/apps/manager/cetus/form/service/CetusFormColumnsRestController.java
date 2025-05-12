package kware.apps.manager.cetus.form.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.form.dto.request.ColumnsChange;
import kware.apps.manager.cetus.form.dto.request.ColumnsSave;
import kware.apps.manager.cetus.form.dto.request.ColumnsSearch;
import kware.apps.manager.cetus.form.dto.response.ColumnsPage;
import kware.apps.manager.cetus.form.dto.response.ColumnsView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value="/cetus/api/form")
@RequiredArgsConstructor
public class CetusFormColumnsRestController {
    private final CetusFormColumnsService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ColumnsSave request) {
        service.save(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Void> update(@PathVariable Long uid, @RequestBody @Valid ColumnsChange request) {
        service.change(uid, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ColumnsPage>> page(ColumnsSearch request, Pageable pageable) {
        return ResponseEntity.ok(service.columns(request, pageable));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<ColumnsView> columns(@PathVariable Long uid) {
        return ResponseEntity.ok(service.column(uid));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> columnsDelete(@PathVariable Long uid) {
        service.deleteColumns(uid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/option/{uid}")
    public ResponseEntity<Void> optionDelete(@PathVariable Long uid) {
        service.deleteOption(uid);
        return ResponseEntity.ok().build();
    }


}
