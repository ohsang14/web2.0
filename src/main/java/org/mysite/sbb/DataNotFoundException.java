package org.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 이 Exception이 throw 되면 클라이언트에게 Http 상태 코드 404를 전달하라.
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1; // 별로 안중요함.
    public DataNotFoundException(String message){
        super(message);
    }
}
