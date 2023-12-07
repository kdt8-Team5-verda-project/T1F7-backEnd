package org.example.dto.responsedto;

import lombok.Getter;

@Getter
public class GetTargetNameDTO {
    private String targetName;
    public GetTargetNameDTO(String targetName){
        this.targetName=targetName;
    }
}
