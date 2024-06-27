package com.als.SMore.study.notice.controller;


import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.study.notice.DTO.MessageResponseDTO;
import com.als.SMore.study.notice.DTO.NoticeRequestDTO;
import com.als.SMore.study.notice.DTO.NoticeResponseDTO;
import com.als.SMore.study.notice.service.NoticeBoardService;
import com.als.SMore.user.util.MemberUtil;
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
    public NoticeResponseDTO getNotice(@PathVariable String studyPK, @PathVariable String noticeBoardPK){
        return noticeBoardService.getNotice(Long.parseLong(studyPK), Long.parseLong(noticeBoardPK));
    }

    @GetMapping
    public List<NoticeResponseDTO> getAllNotice(@PathVariable String studyPK){
        List<NoticeBoard> allNotice = noticeBoardService.getAllNotice(Long.parseLong(studyPK));
        return allNotice.stream()
                .map(NoticeResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public NoticeResponseDTO createNotice(@PathVariable String studyPK, @RequestBody NoticeRequestDTO requestDTO){
        Long memberPk = MemberUtil.getUserPk();
        return noticeBoardService.createNotice(Long.parseLong(studyPK), requestDTO, memberPk);
    }

    @PutMapping("/{noticeBoardPK}")
    public NoticeResponseDTO updateNotice(@PathVariable String studyPK, @PathVariable String noticeBoardPK, @RequestBody NoticeRequestDTO requestDTO){
        Long memberPk = MemberUtil.getUserPk();
        return noticeBoardService.updateNotice(Long.parseLong(studyPK), Long.parseLong(noticeBoardPK), requestDTO , memberPk);
    }

    @DeleteMapping("/{noticeBoardPK}")
    public MessageResponseDTO deleteNotice(@PathVariable String studyPK, @PathVariable String noticeBoardPK){
        Long memberPk = MemberUtil.getUserPk();
        return noticeBoardService.deleteNotice(Long.parseLong(studyPK), Long.parseLong(noticeBoardPK), memberPk);
    }

}
