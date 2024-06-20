package com.als.SMore.user.service;

import com.als.SMore.domain.entity.Member;
import com.als.SMore.domain.repository.MemberRepository;
import com.als.SMore.user.util.MemberUtil;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsFileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String PROFILE_IMG_DIR = "profile/";
    private String member_IMG_dir = "member/";

    private final MemberRepository memberRepository;

    // 단일 파일 저장
    @Transactional
    public String saveFile(MultipartFile file) {

        Long userPk = MemberUtil.getUserPk();

        Member member = memberRepository.findById(userPk).get();

        String profileImg = member.getProfileImg();
        String splitStr = ".com/";

        if (profileImg.lastIndexOf(splitStr) == -1){
            splitStr =".net/";
        }

        profileImg = profileImg.substring(profileImg.lastIndexOf(splitStr) + splitStr.length());

        log.info("회원의 프로필 : {}",profileImg);

        if (find("dir",profileImg)){
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket,PROFILE_IMG_DIR+profileImg));
        }

        String randomFilename = generateRandomFilename(file);

        log.info("File upload started: " + randomFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(bucket, PROFILE_IMG_DIR+randomFilename, file.getInputStream(), metadata);
        } catch (AmazonS3Exception e) {
            log.error("Amazon S3 error while uploading file: " + e.getMessage());
            throw new IllegalArgumentException("에러");
        } catch (SdkClientException e) {
            log.error("AWS SDK client error while uploading file: " + e.getMessage());
            throw new IllegalArgumentException("에러");
        } catch (IOException e) {
            log.error("IO error while uploading file: " + e.getMessage());
            throw new IllegalArgumentException("에러");
        }

        log.info("File upload completed: " + randomFilename);

        String imgPosition = amazonS3Client.getUrl(bucket, PROFILE_IMG_DIR+randomFilename).toString();

        Member modifiedMember = member.toBuilder().profileImg(imgPosition).build();

        log.info("바꾼 이미지 주소 : {}",modifiedMember.getProfileImg());

        memberRepository.save(modifiedMember);

        return amazonS3Client.getUrl(bucket, randomFilename).toString();
    }

    // 랜덤파일명 생성 (파일명 중복 방지)
    private String generateRandomFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = validateFileExtension(originalFilename);
        String randomFilename = UUID.randomUUID() + "." + fileExtension;
        return randomFilename;
    }

    // 파일 확장자 체크
    private String validateFileExtension(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "png", "gif", "jpeg");

        if (!allowedExtensions.contains(fileExtension)) {
            throw new IllegalArgumentException("에러");
        }
        return fileExtension;
    }

    public boolean find(String dir,String file) {
        String key = "";

        // 버킷의 검색을 위해 객체 생성
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();

        listObjectsRequest.setBucketName(bucket);

        // 디렉토리 부분이 null인지 확인
        if(!dir.equals("")){
            listObjectsRequest.setPrefix(dir+"/");
        }

        // listObjectsRequest 구분자 등록하기
        listObjectsRequest.setDelimiter("/");
        // 찾는 오브젝트를 저장
        ObjectListing s3Objects;
        // 찾는 파일의 이름 저장
        String fileName;

        s3Objects = amazonS3Client.listObjects(listObjectsRequest);

        for (S3ObjectSummary objectSummary : s3Objects.getObjectSummaries()){
            key = objectSummary.getKey();
            log.info("key가 무엇일까요? : {}" ,key);
            if (objectSummary.getKey().contains(file)){
                return true;
            }

        }

        return false;

    }
}
