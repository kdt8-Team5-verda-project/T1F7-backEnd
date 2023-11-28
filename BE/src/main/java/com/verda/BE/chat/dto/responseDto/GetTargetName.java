package com.verda.BE.chat.dto.responseDto;

import lombok.Getter;

@Getter
public class GetTargetName {
    private String targetName;

    public GetTargetName(String target){
        this.targetName=target;
    }
}
