package com.dingdong.picmap.domain.user.dto.response;

import com.dingdong.picmap.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserUpdateResponseDto {

      private final Long id;
      private final String nickname;
      private final String introduce;
      private final String status;

      public UserUpdateResponseDto(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.introduce = user.getIntroduce();
            this.status = user.getStatus();
      }

}
