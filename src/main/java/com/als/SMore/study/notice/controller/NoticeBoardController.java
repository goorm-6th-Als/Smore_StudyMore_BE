package com.als.SMore.study.notice.controller;


import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.als.SMore.study.notice.DTO.NoticeResponseDTO;
import com.als.SMore.study.notice.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public NoticeResponseDTO createNotice(@PathVariable Long studyPK, @RequestBody NoticeRequestDTO requestDTO){
        //방장 본인 인지 검증하는 로직 추가 되어야함.
        System.out.println(studyPK);
        System.out.println(requestDTO);
        return noticeBoardService.createNotice(studyPK, requestDTO);
    }


}
