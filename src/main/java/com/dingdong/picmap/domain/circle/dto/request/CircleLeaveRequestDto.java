package com.dingdong.picmap.domain.circle.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CircleLeaveRequestDto {

      private Long circleId;
      private List<Long> userIdList;
}
