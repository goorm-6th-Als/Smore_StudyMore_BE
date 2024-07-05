package com.als.SMore.study.management.service;

import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.entity.StudyBoard;
import com.als.SMore.domain.entity.StudyDetail;
import com.als.SMore.domain.entity.StudyMember;
import com.als.SMore.domain.repository.StudyBoardRepository;
import com.als.SMore.domain.repository.StudyDetailRepository;
import com.als.SMore.domain.repository.StudyMemberRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomException;
import com.als.SMore.study.management.DTO.StudyBoardUpdateDTO;
import com.als.SMore.study.management.DTO.StudyDataDTO;
import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import com.als.SMore.study.management.mapper.StudyBoardMapper;
import com.als.SMore.study.management.mapper.StudyDataMapper;
import com.als.SMore.study.management.mapper.StudyUpdateMapper;
import com.als.SMore.user.mypage.service.AwsFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StudyManagementService {
    private static final Logger logger = LoggerFactory.getLogger(StudyManagementService.class);
    private final AwsFileService awsFileService;
    private final StudyRepository studyRepository;
    private final StudyDetailRepository studyDetailRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final StudyMemberRepository studyMemberRepository;

    /**
     * 기존 스터디, 스터디 보드 GET 메서드
     *
     * @param studyPk        확인할 스터디의 PK
     * @return 현재 스터디, 스터디 보드의 데이터 반환
     */
    @Transactional
    public StudyDataDTO getStudyData(Long studyPk) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 입니다.: " + studyPk));
        StudyDetail studyDetail = studyDetailRepository.findByStudyPk(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("스터디 정보가 존재하지 않습니다: " + studyPk));
        StudyBoard studyBoard = studyBoardRepository.findByStudyStudyPk(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다.: " + studyPk));
        return StudyDataMapper.toDTO(study, studyDetail, studyBoard);
    }

    /**
     * 스터디를 업데이트하는 메서드
     *
     * @param studyPk        업데이트할 스터디의 PK
     * @param studyUpdateDTO 업데이트 할 스터디 보드 DTO
     * @return 업데이트된 스터디 정보를 담은 StudyUpdateDTO 객체
     */
    @Transactional
    public StudyUpdateDTO updateStudy(Long studyPk, StudyUpdateDTO studyUpdateDTO) {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 입니다.: " + studyPk));

        StudyDetail studyDetail = studyDetailRepository.findByStudy(study);
        studyDetail = StudyUpdateMapper.updateStudyDetail(studyDetail, studyUpdateDTO);
        studyDetailRepository.save(studyDetail);

        return StudyUpdateMapper.fromEntity(studyDetail);
    }


    /**
     * 스터디 보드를 업데이트하는 메서드
     *
     * @param studyPk             업데이트할 스터디의 PK
     * @param studyBoardUpdateDTO 업데이트 할 스터디 보드 DTO
     * @param image               업데이트할 이미지 파일
     * @return 업데이트된 스터디 보드 정보를 담은 StudyBoard 객체
     */
    @Transactional
    public StudyBoard updateStudyBoard(Long studyPk, StudyBoardUpdateDTO studyBoardUpdateDTO, MultipartFile image) {
        StudyBoard studyBoard = studyBoardRepository.findByStudyStudyPk(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. " + studyPk));

        // 이미지 파일이 있는 경우에만 S3에 저장하고, 없으면 기존 이미지 URL 유지
        String imageUrl =
                (image != null && !image.isEmpty()) ? awsFileService.saveStudyFile(image) : studyBoard.getImageUri();
        studyBoard = StudyBoardMapper.updateStudyBoard(studyBoard, studyBoardUpdateDTO, imageUrl);

        return studyBoardRepository.save(studyBoard);
    }

    /**
     * 스터디를 삭제하는 메서드
     *
     * @param studyPk 삭제할 스터디의 PK
     */
    @Transactional
    public void deleteStudy(Long studyPk) throws CustomException {
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 입니다.: " + studyPk));
        studyRepository.delete(study);
    }

    /**
     * 스터디 멤버를 퇴출하는 메서드
     *
     * @param studyPk  스터디 PK
     * @param memberPk 퇴출할 멤버 PK
     * @return 퇴출된 멤버의 닉네임
     */
    @Transactional
    public String expelStudyMember(Long studyPk, Long memberPk) {
        StudyMember studyMember = studyMemberRepository.findByStudyStudyPkAndMemberMemberPk(studyPk, memberPk)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 멤버 ID: " + memberPk + " or 스터디 ID: " + studyPk));
        studyMemberRepository.delete(studyMember);
        return studyMember.getMember().getNickName();
    }
}
