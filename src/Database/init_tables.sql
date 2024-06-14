USE smore_DB;

-- Create tables
CREATE TABLE `member`
(
    `member_pk`     BIGINT       NOT NULL COMMENT '멤버PK',
    `user_id`       VARCHAR(255) NOT NULL COMMENT 'ID',
    `user_password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `profile_img`   VARCHAR(255) NULL COMMENT '프로필사진',
    `nick_name`     VARCHAR(255) NOT NULL COMMENT '닉네임',
    `full_name`     VARCHAR(255) NOT NULL COMMENT '이름'
);

CREATE TABLE `study`
(
    `study_pk`   BIGINT       NOT NULL COMMENT '스터디PK',
    `member_pk`  BIGINT       NOT NULL COMMENT '멤버PK',
    `study_name` VARCHAR(255) NOT NULL COMMENT '이름'
);

CREATE TABLE `member_token`
(
    `member_pk`     BIGINT       NOT NULL COMMENT '멤버PK',
    `access_token`  VARCHAR(255) NOT NULL COMMENT '액세스 토큰',
    `refresh_token` VARCHAR(255) NOT NULL COMMENT '리프레시 토큰'
);

CREATE TABLE `study_board`
(
    `study_board_pk` BIGINT       NOT NULL COMMENT '홍보게시판PK',
    `study_pk`       BIGINT       NOT NULL COMMENT '스터디PK',
    `ad_title`       VARCHAR(255) NOT NULL COMMENT '제목',
    `ad_content`     TEXT         NULL COMMENT '내용',
    `ad_summary`     VARCHAR(255) NULL COMMENT '내용 요약',
    `create_date`    DATE         NULL COMMENT '작성날짜',
    `modify_date`    DATE         NULL COMMENT '수정날짜',
    `close_date`     DATE         NULL COMMENT '마감날짜',
    `member_pk`      BIGINT       NOT NULL COMMENT '멤버PK'
);

CREATE TABLE `study_schedule`
(
    `study_schedule_pk` BIGINT       NOT NULL COMMENT '공유일정PK',
    `study_pk`          BIGINT       NOT NULL COMMENT '스터디PK',
    `member_pk`         BIGINT       NOT NULL COMMENT '멤버PK',
    `schedule_status`   VARCHAR(255) NULL COMMENT '상태',
    `target_date`       DATETIME     NULL COMMENT '목표날짜',
    `create_date`       DATETIME     NULL COMMENT '작성날짜'
);

CREATE TABLE `attendance_check`
(
    `attendance_check_pk` BIGINT   NOT NULL COMMENT '출석체크PK',
    `member_pk`           BIGINT   NOT NULL COMMENT '멤버PK',
    `study_pk`            BIGINT   NOT NULL COMMENT '스터디PK',
    `attendance_date`     DATETIME NULL COMMENT '출석체크 시간',
    `attendance_date_end` DATETIME NULL COMMENT '공부 종료시간'
);

CREATE TABLE `chatting`
(
    `chatting_pk`      BIGINT   NOT NULL COMMENT '채팅PK',
    `member_pk`        BIGINT   NOT NULL COMMENT '멤버PK',
    `chatting_room_pk` BIGINT   NOT NULL COMMENT '채팅방PK',
    `chat_content`     TEXT     NOT NULL COMMENT '내용',
    `create_date`      DATETIME NOT NULL COMMENT '작성 시간'
);

CREATE TABLE `problem`
(
    `problem_pk`           BIGINT  NOT NULL COMMENT '문제PK',
    `member_pk`            BIGINT  NOT NULL COMMENT '멤버PK',
    `problem_code_bank_pk` BIGINT  NOT NULL COMMENT '스터디 문제 은행 PK',
    `problem_content`      TEXT    NULL COMMENT '내용',
    `problem_answer`       INTEGER NULL COMMENT '답',
    `problem_explanation`  TEXT    NULL COMMENT '문제 설명',
    `create_date`          DATE    NULL COMMENT '작성 날짜'
);

CREATE TABLE `study_member`
(
    `study_member_pk` BIGINT       NOT NULL COMMENT '스터디 회원 PK',
    `role`            VARCHAR(255) NOT NULL COMMENT '역할',
    `study_pk`        BIGINT       NOT NULL COMMENT '스터디PK',
    `member_pk`       BIGINT       NOT NULL COMMENT '멤버PK'
);

CREATE TABLE `calender`
(
    `calender_pk`      BIGINT   NOT NULL COMMENT 'PK',
    `calender_content` TEXT     NULL COMMENT '내용',
    `calendar_date`    DATETIME NOT NULL COMMENT '날짜',
    `study_pk`         BIGINT   NOT NULL COMMENT '스터디PK',
    `member_pk`        BIGINT   NOT NULL COMMENT '회원PK'
);

CREATE TABLE `study_schedule_place_date`
(
    `study_schedule_place_date_pk` BIGINT NOT NULL COMMENT '위치정보PK',
    `study_schedule_pk`            BIGINT NOT NULL COMMENT '공유일정PK',
    `latitude`                     DOUBLE NOT NULL COMMENT '위도',
    `longitude`                    DOUBLE NOT NULL COMMENT '경도'
);

CREATE TABLE `problem_options`
(
    `problem_options_pk` BIGINT       NOT NULL COMMENT '문제 선택지 PK',
    `problem_pk`         BIGINT       NOT NULL COMMENT '문제PK',
    `options_num`        INTEGER      NOT NULL COMMENT '문제 번호',
    `options_content`    VARCHAR(255) NOT NULL COMMENT '내용'
);

CREATE TABLE `chatting_room`
(
    `chatting_room_pk` BIGINT       NOT NULL COMMENT '채팅방PK',
    `study_pk`         BIGINT       NOT NULL COMMENT '스터디PK',
    `room_name`        VARCHAR(255) NULL COMMENT '이름'
);

CREATE TABLE `personal_todo`
(
    `personal_todo_pk` BIGINT       NOT NULL COMMENT '일정PK',
    `study_pk`         BIGINT       NOT NULL COMMENT '스터디PK',
    `member_pk`        BIGINT       NOT NULL COMMENT '멤버PK',
    `schedule_status`  VARCHAR(255) NOT NULL COMMENT '달성상태',
    `schedule_content` TEXT         NULL COMMENT '내용',
    `schedule_date`    DATETIME     NOT NULL COMMENT '스케줄 날짜',
    `create_date`      DATETIME     NOT NULL COMMENT '생성 날짜'
);

CREATE TABLE `problem_type`
(
    `problem_type_pk` BIGINT       NOT NULL COMMENT 'PK',
    `problem_pk`      BIGINT       NOT NULL COMMENT '문제PK',
    `type_content`    VARCHAR(255) NULL COMMENT '타입 내용'
);

CREATE TABLE `study_problem_bank`
(
    `problem_code_bank_pk` BIGINT       NOT NULL COMMENT '스터디 문제 은행 PK',
    `study_pk`             BIGINT       NOT NULL COMMENT '스터디PK',
    `study_name`           VARCHAR(255) NOT NULL COMMENT '작성된 스터디',
    `group_name`           VARCHAR(255) NOT NULL COMMENT '문제 그룹 이름'
);

CREATE TABLE `study_detail`
(
    `study_pk`   BIGINT       NOT NULL COMMENT '스터디디테일PK',
    `image_uri`  VARCHAR(255) NULL COMMENT '대문',
    `max_people` INTEGER      NOT NULL COMMENT '최대인원',
    `overview`   TEXT         NULL COMMENT '개요',
    `open_time`  TIME         NOT NULL DEFAULT '00:00:00' COMMENT '오픈시간',
    `close_time` TIME         NOT NULL DEFAULT '23:59:59' COMMENT '클로즈시간'
);

CREATE TABLE `notice_board`
(
    `notice_board_pk` BIGINT       NOT NULL COMMENT '스터디 문제 은행 PK',
    `study_pk`        BIGINT       NOT NULL COMMENT '스터디PK',
    `notice_title`    VARCHAR(255) NOT NULL COMMENT '작성된 스터디',
    `notice_content`  VARCHAR(255) NOT NULL COMMENT '문제 그룹 이름'
);

ALTER TABLE `member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
                                            `member_pk`
        );

ALTER TABLE `study`
    ADD CONSTRAINT `PK_STUDY` PRIMARY KEY (
                                           `study_pk`
        );

ALTER TABLE `member_token`
    ADD CONSTRAINT `PK_MEMBER_TOKEN` PRIMARY KEY (
                                                  `member_pk`
        );

ALTER TABLE `study_board`
    ADD CONSTRAINT `PK_STUDY_BOARD` PRIMARY KEY (
                                                 `study_board_pk`
        );

ALTER TABLE `study_schedule`
    ADD CONSTRAINT `PK_STUDY_SCHEDULE` PRIMARY KEY (
                                                    `study_schedule_pk`
        );

ALTER TABLE `attendance_check`
    ADD CONSTRAINT `PK_ATTENDANCE_CHECK` PRIMARY KEY (
                                                      `attendance_check_pk`
        );

ALTER TABLE `chatting`
    ADD CONSTRAINT `PK_CHATTING` PRIMARY KEY (
                                              `chatting_pk`
        );

ALTER TABLE `problem`
    ADD CONSTRAINT `PK_PROBLEM` PRIMARY KEY (
                                             `problem_pk`
        );

ALTER TABLE `study_member`
    ADD CONSTRAINT `PK_STUDY_MEMBER` PRIMARY KEY (
                                                  `study_member_pk`
        );

ALTER TABLE `calender`
    ADD CONSTRAINT `PK_CALENDER` PRIMARY KEY (
                                              `calender_pk`
        );

ALTER TABLE `study_schedule_place_date`
    ADD CONSTRAINT `PK_STUDY_SCHEDULE_PLACE_DATE` PRIMARY KEY (
                                                               `study_schedule_place_date_pk`
        );

ALTER TABLE `problem_options`
    ADD CONSTRAINT `PK_PROBLEM_OPTIONS` PRIMARY KEY (
                                                     `problem_options_pk`
        );

ALTER TABLE `chatting_room`
    ADD CONSTRAINT `PK_CHATTING_ROOM` PRIMARY KEY (
                                                   `chatting_room_pk`
        );

ALTER TABLE `personal_todo`
    ADD CONSTRAINT `PK_PERSONAL_TODO` PRIMARY KEY (
                                                   `personal_todo_pk`
        );

ALTER TABLE `problem_type`
    ADD CONSTRAINT `PK_PROBLEM_TYPE` PRIMARY KEY (
                                                  `problem_type_pk`
        );

ALTER TABLE `study_problem_bank`
    ADD CONSTRAINT `PK_STUDY_PROBLEM_BANK` PRIMARY KEY (
                                                        `problem_code_bank_pk`
        );

ALTER TABLE `study_detail`
    ADD CONSTRAINT `PK_STUDY_DETAIL` PRIMARY KEY (
                                                  `study_pk`
        );

ALTER TABLE `notice_board`
    ADD CONSTRAINT `PK_NOTICE_BOARD` PRIMARY KEY (
                                                  `notice_board_pk`
        );

ALTER TABLE `study`
    ADD CONSTRAINT `FK_member_TO_study_1` FOREIGN KEY (
                                                       `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `member_token`
    ADD CONSTRAINT `FK_member_TO_member_token_1` FOREIGN KEY (
                                                              `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `study_board`
    ADD CONSTRAINT `FK_study_TO_study_board_1` FOREIGN KEY (
                                                            `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `study_board`
    ADD CONSTRAINT `FK_member_TO_study_board_1` FOREIGN KEY (
                                                             `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `study_schedule`
    ADD CONSTRAINT `FK_study_TO_study_schedule_1` FOREIGN KEY (
                                                               `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `study_schedule`
    ADD CONSTRAINT `FK_member_TO_study_schedule_1` FOREIGN KEY (
                                                                `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `attendance_check`
    ADD CONSTRAINT `FK_member_TO_attendance_check_1` FOREIGN KEY (
                                                                  `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `attendance_check`
    ADD CONSTRAINT `FK_study_TO_attendance_check_1` FOREIGN KEY (
                                                                 `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `chatting`
    ADD CONSTRAINT `FK_member_TO_chatting_1` FOREIGN KEY (
                                                          `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `chatting`
    ADD CONSTRAINT `FK_chatting_room_TO_chatting_1` FOREIGN KEY (
                                                                 `chatting_room_pk`
        )
        REFERENCES `chatting_room` (
                                    `chatting_room_pk`
            );

ALTER TABLE `problem`
    ADD CONSTRAINT `FK_member_TO_problem_1` FOREIGN KEY (
                                                         `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `problem`
    ADD CONSTRAINT `FK_study_problem_bank_TO_problem_1` FOREIGN KEY (
                                                                     `problem_code_bank_pk`
        )
        REFERENCES `study_problem_bank` (
                                         `problem_code_bank_pk`
            );

ALTER TABLE `study_member`
    ADD CONSTRAINT `FK_study_TO_study_member_1` FOREIGN KEY (
                                                             `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `study_member`
    ADD CONSTRAINT `FK_member_TO_study_member_1` FOREIGN KEY (
                                                              `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `calender`
    ADD CONSTRAINT `FK_study_TO_calender_1` FOREIGN KEY (
                                                         `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `calender`
    ADD CONSTRAINT `FK_member_TO_calender_1` FOREIGN KEY (
                                                          `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `study_schedule_place_date`
    ADD CONSTRAINT `FK_study_schedule_TO_study_schedule_place_date_1` FOREIGN KEY (
                                                                                   `study_schedule_pk`
        )
        REFERENCES `study_schedule` (
                                     `study_schedule_pk`
            );

ALTER TABLE `problem_options`
    ADD CONSTRAINT `FK_problem_TO_problem_options_1` FOREIGN KEY (
                                                                  `problem_pk`
        )
        REFERENCES `problem` (
                              `problem_pk`
            );

ALTER TABLE `chatting_room`
    ADD CONSTRAINT `FK_study_TO_chatting_room_1` FOREIGN KEY (
                                                              `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `personal_todo`
    ADD CONSTRAINT `FK_study_TO_personal_todo_1` FOREIGN KEY (
                                                              `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `personal_todo`
    ADD CONSTRAINT `FK_member_TO_personal_todo_1` FOREIGN KEY (
                                                               `member_pk`
        )
        REFERENCES `member` (
                             `member_pk`
            );

ALTER TABLE `problem_type`
    ADD CONSTRAINT `FK_problem_TO_problem_type_1` FOREIGN KEY (
                                                               `problem_pk`
        )
        REFERENCES `problem` (
                              `problem_pk`
            );

ALTER TABLE `study_problem_bank`
    ADD CONSTRAINT `FK_study_TO_study_problem_bank_1` FOREIGN KEY (
                                                                   `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `study_detail`
    ADD CONSTRAINT `FK_study_TO_study_detail_1` FOREIGN KEY (
                                                             `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

ALTER TABLE `notice_board`
    ADD CONSTRAINT `FK_study_TO_notice_board_1` FOREIGN KEY (
                                                             `study_pk`
        )
        REFERENCES `study` (
                            `study_pk`
            );

