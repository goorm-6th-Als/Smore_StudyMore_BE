package com.als.SMore.study.management.service;

import static com.als.SMore.global.CustomErrorCode.CustomErrorCode;

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
import com.als.SMore.study.management.DTO.StudyUpdateDTO;
import com.als.SMore.study.management.mapper.StudyBoardMapper;
import com.als.SMore.study.management.mapper.StudyUpdateMapper;
import com.als.SMore.user.login.util.MemberUtil;
import com.als.SMore.user.mypage.service.AwsFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 스터디를 업데이트하는 메서드
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyUpdateDTO 업데이트 할 스터디 보드 DTO
     * @return 업데이트된 스터디 정보를 담은 StudyUpdateDTO 객체
     */
    @Transactional
    public StudyUpdateDTO updateStudy(Long studyPk, StudyUpdateDTO studyUpdateDTO) {
        Long memberPk = validateAndGetMemberPk(studyPk);

        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));

        StudyDetail studyDetail = studyDetailRepository.findByStudy(study);
        studyDetail = StudyUpdateMapper.updateStudyDetail(studyDetail, studyUpdateDTO);
        studyDetailRepository.save(studyDetail);

        return StudyUpdateMapper.fromEntity(studyDetail);
    }


    /**
     * 스터디 보드를 업데이트하는 메서드
     * @param studyPk 업데이트할 스터디의 PK
     * @param studyBoardUpdateDTO 업데이트 할 스터디 보드 DTO
     * @param image 업데이트할 이미지 파일
     * @return 업데이트된 스터디 보드 정보를 담은 StudyBoard 객체
     */
    @Transactional
    public StudyBoard updateStudyBoard(Long studyPk, StudyBoardUpdateDTO studyBoardUpdateDTO, MultipartFile image) {
        validateAndGetMemberPk(studyPk);

        StudyBoard studyBoard = studyBoardRepository.findByStudyStudyPk(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study board ID: " + studyPk));

        // 이미지 파일이 있는 경우에만 S3에 저장하고, 없으면 기존 이미지 URL 유지
        String imageUrl = (image != null && !image.isEmpty()) ? awsFileService.saveStudyFile(image) : studyBoard.getImageUri();
        studyBoard = StudyBoardMapper.updateStudyBoard(studyBoard, studyBoardUpdateDTO, imageUrl);

        return studyBoardRepository.save(studyBoard);
    }

    /**
     * 스터디를 삭제하는 메서드
     * @param studyPk 삭제할 스터디의 PK
     */
    @Transactional
    public void deleteStudy(Long studyPk) throws CustomException {
        Long memberPk = validateAndGetMemberPk(studyPk);
        if (!isAdmin(studyPk, memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER);
        }
        if (!isOnlyAdmin(studyPk)) {
            throw new CustomException(CustomErrorCode.STILL_EXISTS_MEMBERS);
        }
        Study study = studyRepository.findById(studyPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 study ID: " + studyPk));
        studyRepository.delete(study);
    }

    /**
     * 스터디 멤버를 퇴출하는 메서드
     * @param studyPk 스터디 PK
     * @param memberPk 퇴출할 멤버 PK
     * @return 퇴출된 멤버의 닉네임
     */
    @Transactional
    public String expelStudyMember(Long studyPk, Long memberPk) {
        validateAndGetMemberPk(studyPk); // 스터디 장인지 확인

        StudyMember studyMember = studyMemberRepository.findByStudyStudyPkAndMemberMemberPk(studyPk, memberPk)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버 ID: " + memberPk + " or 스터디 ID: " + studyPk));
        studyMemberRepository.delete(studyMember);
        return studyMember.getMember().getNickName();
    }

    /**
     * 스터디 장인지 및 유효한 멤버인지 확인하는 메서드
     * @param studyPk 스터디 PK
     * @return 유효한 멤버 PK
     */
    private Long validateAndGetMemberPk(Long studyPk) {
        Long memberPk = MemberUtil.getUserPk();
        if (!isAdmin(studyPk, memberPk)) {
            throw new CustomException(CustomErrorCode.NOT_AUTHORIZED_REQUEST_MEMBER);
        }
        return memberPk;
    }

    /**
     * 스터디 장인지 확인하는 메서드
     * @param studyPk 스터디 PK
     * @param memberPk 멤버 PK
     * @return 스터디 장이면 true, 아니면 false 반환
     */
    @Transactional(readOnly = true)
    public boolean isAdmin(Long studyPk, Long memberPk) {
        return studyMemberRepository.existsByStudyStudyPkAndMemberMemberPkAndRole(studyPk, memberPk, "admin");
    }

    /**
     * 스터디장만 존재하는지 확인하는 메서드
     * @param studyPk 스터디 PK
     * @return 스터디에 admin만 존재하면 true, 아니면 false 반환
     */
    @Transactional(readOnly = true)
    public boolean isOnlyAdmin(Long studyPk) {
        long totalMembers = studyMemberRepository.countByStudyStudyPk(studyPk);
        long adminMembers = studyMemberRepository.countByStudyStudyPkAndRole(studyPk, "admin");
        return totalMembers == adminMembers;
    }
}
