package com.c104.seolo.global.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SeoloErrorResponse {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public SeoloErrorResponse(Builder builder) {
        this.httpStatus = builder.httpStatus;
        this.errorCode = builder.errorCode;
        this.message = builder.message;
    }


    public static class Builder {
        private HttpStatus httpStatus;
        private String errorCode;
        private String message;


        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public SeoloErrorResponse build() {
            return new SeoloErrorResponse(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
