package com.als.SMore.study.management.controller;

import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.study.management.DTO.ExpelMemberDTO;
import com.als.SMore.study.management.DTO.StudyBoardUpdateDTO;
import com.als.SMore.study.management.DTO.StudyDataDTO;
import com.als.SMore.study.management.DTO.StudyMemberWithOutAdminDTO;
import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import com.als.SMore.study.management.service.StudyManagementService;
import com.als.SMore.user.login.util.JwtRole;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/study/{studyPk}/management")
@RequiredArgsConstructor
public class StudyManagementController {

    private final StudyManagementService studyManagementService;

    /**
     * 스터디, 스터디 보드 정보 READ
     * @param studyPk 조회 할 studyPk
     * @return StudyDataDTO StudyDetail + StudyBoard
     */
    @JwtRole
    @GetMapping
    public ResponseEntity<StudyDataDTO> getStudyData(
            @PathVariable Long studyPk) {
        StudyDataDTO getStudy = studyManagementService.getStudyData(studyPk);
        return ResponseEntity.ok(getStudy);
    }

    /**
     * 스터디에 참여중인 방장을 제외한 모든 멤버를 조회
     *
     * @param studyPk 스터디 PK
     * @return 스터디 멤버 목록과 함께 OK 응답 반환
     */
    @JwtRole
    @GetMapping("/members")
    public ResponseEntity<List<StudyMemberWithOutAdminDTO>> getStudyMembers(
            @PathVariable Long studyPk) {
        List<StudyMemberWithOutAdminDTO> studyMembers = studyManagementService.getAllStudyMembers(studyPk);
        return ResponseEntity.ok(studyMembers);
    }

    /**
     * 스터디 정보 업데이트
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyUpdateDTO 업데이트할 스터디 정보를 담은 DTO
     * @return 업데이트된 스터디 정보를 담은 DTO와 함께 응답
     */
    @JwtRole
    @PutMapping
    public ResponseEntity<StudyUpdateDTO> updateStudy(
            @PathVariable Long studyPk,
            @RequestBody StudyUpdateDTO studyUpdateDTO) {
        StudyUpdateDTO updatedStudy = studyManagementService.updateStudy(studyPk, studyUpdateDTO);
        return ResponseEntity.ok(updatedStudy);
    }

    /**
     * 스터디 보드 업데이트
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyBoardUpdateDTO 업데이트할 스터디 보드 정보를 담은 DTO
     * @param image 업데이트할 이미지 파일
     * @return 업데이트된 스터디 보드 정보를 담은 DTO와 함께 응답
     */
    @JwtRole
    @PutMapping("/board")
    public ResponseEntity<StudyBoard> updateStudyBoard(
            @PathVariable Long studyPk,
            @RequestPart("studyBoardUpdateDTO") StudyBoardUpdateDTO studyBoardUpdateDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) {  // 이미지 파일이 없어도 되게.
        StudyBoard updatedBoard = studyManagementService.updateStudyBoard(studyPk, studyBoardUpdateDTO, image);
        return ResponseEntity.ok(updatedBoard);
    }

    /**
     * 스터디 삭제
     * 스터디 멤버가 존재하지 않아야 함.
     * @param studyPk 삭제할 스터디의 PK
     * @return No Content 상태의 응답
     */
    @JwtRole
    @DeleteMapping
    public ResponseEntity<Void> deleteStudy(
            @PathVariable Long studyPk) {
        studyManagementService.deleteStudy(studyPk);
        return ResponseEntity.noContent().build();
    }


    /**
     * 스터디 멤버 퇴출
     * @param studyPk 스터디 PK
     * @param expelMemberDTO 퇴출할 멤버 PK
     * @return 퇴출된 멤버의 닉네임을 포함한 메시지
     */
    @JwtRole
    @DeleteMapping("/expel")
    public ResponseEntity<String> expelStudyMember(
            @PathVariable Long studyPk,
            @RequestBody ExpelMemberDTO expelMemberDTO) {
        String nickname = studyManagementService.expelStudyMember(studyPk, expelMemberDTO.getMemberPk());
        return ResponseEntity.ok(nickname + " 님을 퇴출했습니다.");
    }
}
