package kware.apps.thirdeye.mobigen.approveddataset.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.Instant;

/** 진열 데이터셋 제목 백필 1회 실행 결과 */
@Getter
public class TitleBackfillResult {

    public enum Status { DONE, STOPPED, SKIPPED, ALREADY_RUNNING }

    private final Status status;
    private final int targets;      // 제목이 비어 있던 대상 수
    private final int updated;      // 제목을 채운 수
    private final int failed;       // 모비젠에서 제목을 받지 못한 수
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING)   // ISO-8601 문자열로 응답
    private final Instant finishedAt;

    public TitleBackfillResult(Status status, int targets, int updated, int failed, String message, Instant finishedAt) {
        this.status = status;
        this.targets = targets;
        this.updated = updated;
        this.failed = failed;
        this.message = message;
        this.finishedAt = finishedAt;
    }

    public static TitleBackfillResult skipped(String message, Instant at) {
        return new TitleBackfillResult(Status.SKIPPED, 0, 0, 0, message, at);
    }

    public static TitleBackfillResult alreadyRunning(Instant at) {
        return new TitleBackfillResult(Status.ALREADY_RUNNING, 0, 0, 0, "이미 실행 중입니다.", at);
    }
}
