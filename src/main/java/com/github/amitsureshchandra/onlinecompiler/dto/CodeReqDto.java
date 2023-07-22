package com.github.amitsureshchandra.onlinecompiler.dto;

public class CodeReqDto {
    private String code;
    private String compiler;

    public CodeReqDto(String code, String compiler) {
        this.code = code;
        this.compiler = compiler;
    }

    public CodeReqDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompiler() {
        return compiler;
    }

    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }
}
