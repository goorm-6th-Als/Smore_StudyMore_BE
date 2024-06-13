package com.als.SMore.study.notice.validator;


import com.als.SMore.domain.entity.NoticeBoard;
import com.als.SMore.domain.entity.Study;
import com.als.SMore.domain.repository.NoticeBoardRepository;
import com.als.SMore.domain.repository.StudyRepository;
import com.als.SMore.global.CustomErrorCode;
import com.als.SMore.global.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeValidator {

    private final StudyRepository studyRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    public void validateTitleLength(String str) {
        if (str == null || str.length() < 1 || str.length() > 30) {
            throw new CustomException(CustomErrorCode.INVALID_TITLE_NAME);
        }
    }

    public Study findStudyByStudyPk(Long studyPK) {
        return studyRepository.findById(studyPK)
                .orElseThrow(()-> new CustomException(CustomErrorCode.NOT_EXIST_PAGE));
    }

    public NoticeBoard findNoticeByBoardPkAndStudy(Long noticeBoardPK, Study study) {
        return noticeBoardRepository.findByNoticeBoardPkAndStudy(noticeBoardPK, study)
                .orElseThrow(()-> new CustomException(CustomErrorCode.NOT_EXIST_PAGE));
    }
}
