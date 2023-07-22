package com.github.amitsureshchandra.onlinecompiler.dto;

public class CodeReqDto {
    private String code;
    private String compiler;
    private String input;

    public CodeReqDto(String code, String compiler, String input) {
        this.code = code;
        this.compiler = compiler;
        this.input = input;
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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
