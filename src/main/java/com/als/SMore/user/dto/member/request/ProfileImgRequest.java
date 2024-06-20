package com.als.SMore.user.dto.member.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProfileImgRequest {
    private MultipartFile profileImage;
}
