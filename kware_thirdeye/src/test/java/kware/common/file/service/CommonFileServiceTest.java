package kware.common.file.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import kware.common.file.domain.CommonFile;
import kware.common.file.domain.CommonFileDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CommonFileServiceTest {

    @Mock
    private CetusTusFileService tusFileService;

    @Mock
    private CommonFileDao dao;

    @TempDir
    Path tempDir;

    private CommonFileService service;

    @BeforeEach
    void setUp() {
        service = new CommonFileService(tusFileService, dao);
        ReflectionTestUtils.setField(service, "tusTemp", tempDir.toString());
    }

    @Test
    void downloadTemp_상위_폴더로_올라가는_fileId는_거부한다() {
        ResponseEntity<?> response = service.downloadTemp(request("../outside.txt"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("invalid fileId");
    }

    @Test
    void downloadTemp_절대_경로_fileId는_거부한다() {
        String outside = tempDir.getParent().resolve("outside.txt").toString();

        ResponseEntity<?> response = service.downloadTemp(request(outside));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void downloadTemp_임시_저장소에_없는_파일은_404다() {
        ResponseEntity<?> response = service.downloadTemp(request("missing.bin"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void downloadTemp_임시_저장소_안_파일은_본문으로_내려준다() throws Exception {
        Path stored = Files.write(tempDir.resolve("upload.bin"), "data".getBytes(StandardCharsets.UTF_8));

        ResponseEntity<?> response = service.downloadTemp(request("upload.bin"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
        assertThat(((FileSystemResource) response.getBody()).getFile()).isEqualTo(stored.toFile());
    }

    @Test
    void fileView_파일_정보가_없으면_404다() {
        when(dao.findFileInfo(any(CommonFile.class))).thenReturn(null);

        ResponseEntity<?> response = service.fileView(request("unknown"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void fileView_저장_경로에_파일이_없으면_404다() {
        CommonFile info = new CommonFile("f1");
        info.setFilePath(tempDir.resolve("gone.png").toString());
        when(dao.findFileInfo(any(CommonFile.class))).thenReturn(info);

        ResponseEntity<?> response = service.fileView(request("f1"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void fileView_파일_형식이_비어_있으면_확장자로_content_type을_정한다() throws Exception {
        File image = Files.write(tempDir.resolve("photo.png"), new byte[] {1, 2, 3}).toFile();
        CommonFile info = new CommonFile("f1");
        info.setFilePath(image.getAbsolutePath());
        info.setExtension("png");
        when(dao.findFileInfo(any(CommonFile.class))).thenReturn(info);

        ResponseEntity<?> response = service.fileView(request("f1"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_PNG);
        assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .isEqualTo("inline; filename=\"photo.png\"");
    }

    @Test
    void fileId가_빈_값이나_문자열_null_undefined면_없는_것으로_본다() {
        assertThat(service.ifFileIdExist(request(""))).isFalse();
        assertThat(service.ifFileIdExist(request("null"))).isFalse();
        assertThat(service.ifFileIdExist(request("undefined"))).isFalse();
        assertThat(service.ifFileIdExist(request("abc"))).isTrue();
    }

    @Test
    void fileId_파라미터가_아예_없으면_오류_없이_없는_것으로_본다() {
        MockHttpServletRequest noParam = new MockHttpServletRequest();

        assertThat(service.ifFileIdExist(noParam)).isFalse();
        assertThat(service.downloadTemp(noParam).getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(service.downloadTemp(noParam).getBody()).isEqualTo("fileId is not found");
    }

    private static MockHttpServletRequest request(String fileId) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("fileId", fileId);
        return request;
    }
}
