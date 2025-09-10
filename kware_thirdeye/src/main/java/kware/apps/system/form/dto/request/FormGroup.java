package kware.apps.system.form.dto.request;

import lombok.Getter;

@Getter
public enum FormGroup {
    SIGNUP("회원가입");

    FormGroup(String label) {
        this.label = label;
    }
    private final String label;
}
