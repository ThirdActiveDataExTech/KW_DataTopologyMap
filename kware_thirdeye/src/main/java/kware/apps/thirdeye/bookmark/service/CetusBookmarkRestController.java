package kware.apps.thirdeye.bookmark.service;

import cetus.user.UserUtil;
import kware.apps.thirdeye.bookmark.dto.request.UserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/bookmark")
public class CetusBookMarkRestController {

    private final CetusBookMarkService service;

    @GetMapping("/list")
    public ResponseEntity page() {
        List<UserBookMarkList> list = service.findUserBookMarkList(new SearchUserBookMark(UserUtil.getUser().getUid()));
        return ResponseEntity.ok(list);
    }

    @PostMapping("/toggle")
    public ResponseEntity toggleLike( @RequestBody @Valid UserBookMarkToggle request ) {
        Boolean toggleLike = service.toggleLike(request);
        return ResponseEntity.ok(toggleLike);
    }

    @DeleteMapping("/{datasetId}")
    public ResponseEntity delete( @PathVariable Long datasetId ) {
        service.deleteBookMark(datasetId);
        return ResponseEntity.ok().build();
    }
}
