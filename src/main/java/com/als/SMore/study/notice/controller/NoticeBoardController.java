package com.als.SMore.study.notice.controller;


import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.study.notice.DTO.MessageResponseDTO;
import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.als.SMore.study.notice.DTO.NoticeResponseDTO;
import com.als.SMore.study.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/study/{studyPK}/notice")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @GetMapping("/{noticeBoardPK}")
    public NoticeResponseDTO getNotice(@PathVariable Long studyPK, @PathVariable Long noticeBoardPK){
        return noticeBoardService.getNotice(studyPK, noticeBoardPK);
    }

    @GetMapping
    public List<NoticeResponseDTO> getAllNotice(@PathVariable Long studyPK){
        List<NoticeBoard> allNotice = noticeBoardService.getAllNotice(studyPK);
        return allNotice.stream()
                .map(NoticeResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public NoticeResponseDTO createNotice(@PathVariable Long studyPK, @RequestBody NoticeRequestDTO requestDTO, @RequestParam String requestorPk){
        //방장 본인 인지 검증하는것 헤더에서 PK가져오는것으로 변경 해야함.
        Long requestorPkLong = Long.parseLong(requestorPk);
        return noticeBoardService.createNotice(studyPK, requestDTO, requestorPkLong);
    }

    @PutMapping("/{noticeBoardPK}")
    public NoticeResponseDTO updateNotice(@PathVariable Long studyPK, @PathVariable Long noticeBoardPK, @RequestBody NoticeRequestDTO requestDTO, @RequestParam String requestorPk){
        //방장 본인 인지 검증하는것 헤더에서 PK가져오는것으로 변경 해야함.


        Long requestorPkLong = Long.parseLong(requestorPk);
        return noticeBoardService.updateNotice(studyPK, noticeBoardPK, requestDTO , requestorPkLong);
    }

    @DeleteMapping("/{noticeBoardPK}")
    public MessageResponseDTO deleteNotice(@PathVariable Long studyPK, @PathVariable Long noticeBoardPK, @RequestParam String requestorPk){
        //방장 본인 인지 검증하는것 헤더에서 PK가져오는것으로 변경 해야함.
        Long requestorPkLong = Long.parseLong(requestorPk);

        return noticeBoardService.deleteNotice(studyPK, noticeBoardPK, requestorPkLong);
    }

}
