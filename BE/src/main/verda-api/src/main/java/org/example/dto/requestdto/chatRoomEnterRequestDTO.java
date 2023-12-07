package org.example.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class chatRoomEnterRequestDTO {
    private long fmId;
    private long userId;
    private long postId;
}
