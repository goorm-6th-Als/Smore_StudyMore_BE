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
    public NoticeResponseDTO createNotice(@PathVariable Long studyPK, @RequestBody NoticeRequestDTO requestDTO){
        //방장 본인 인지 검증하는 로직 추가 되어야함.
        return noticeBoardService.createNotice(studyPK, requestDTO);
    }

    @PutMapping("/{noticeBoardPK}")
    public NoticeResponseDTO updateNotice(@PathVariable Long studyPK, @PathVariable Long noticeBoardPK, @RequestBody NoticeRequestDTO requestDTO){
        //방장 본인 인지 검증하는 로직 추가 되어야함.
        return noticeBoardService.updateNotice(studyPK, noticeBoardPK, requestDTO);
    }

    @DeleteMapping("/{noticeBoardPK}")
    public MessageResponseDTO deleteNotice(@PathVariable Long studyPK, @PathVariable Long noticeBoardPK){
        //방장 본인 인지 검증하는 로직 추가 되어야함.
       return noticeBoardService.deleteNotice(studyPK, noticeBoardPK);
    }

}
