package kware.apps.manager.cetus.enumstatus;


import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public enum UserStatus {

    APPROVED("승인 완료"),
    WAIT("승인 대기");

    private String description;

    UserStatus(String description) {
        this.description = description;
    }
}
