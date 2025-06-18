package com.be.prac_toon.common.utill.imageUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 *  이미지 업로드 서비스
 */
@Service
public class ImageUploadService {

    // 1. application.yml에서 업로드 경로를 읽어옵니다.
    @Value("${custom.upload-path}")
    private String uploadPath;

    public String storeFile(MultipartFile multipartFile) throws IOException {

        // 2. 파일이 비어있는지 확인
        if (multipartFile.isEmpty()) {
            return null;
        }

        // 1. 확장자 검증 (원칙 2 : 서버가 허락한 확장자(jpg, png, ..)만 허용)
        String originalFilename = multipartFile.getOriginalFilename();  // 원본 파일 이름 가져오기
        String originalExtension = extractExtension(originalFilename);  // 확장자 추출

        if (!isSupportedExtension(originalExtension)) {
            // 지원하지 않는 확장자일 경우 예외 처리
            throw new IllegalArgumentException("지원하지 않는 이미지 확장자입니다: " + originalExtension);
        }

        // 2. 고유한 파일 이름 생성 (원칙 1 : 사용자 입력이 아닌 서버에서 고유한 새 이름 생성)
        String newFilename = UUID.randomUUID().toString() + "." + originalExtension;

        // 3. 파일 저장
        // 실제로는 여기서 리사이징/압축/포맷 변환 로직이 추가되어야 합니다. (원칙 3 : 최적화 - 리사이징 및 압축, 포맷 변환)
        // 예시에서는 일단 원본 파일을 그대로 저장합니다.
        File newFile = new File(uploadPath + newFilename);
        multipartFile.transferTo(newFile);

        // 4. 데이터베이스에 저장할 상대 경로 반환
        return "/uploads/" + newFilename; // WebConfig에 설정한 URL 경로
    }

    // 파일 이름에서 확장자를 추출하는 헬퍼 메소드
    private String extractExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("잘못된 형식의 파일 이름입니다.", e);
        }
    }

    // 지원하는 확장자인지 확인하는 -헬퍼 메소드
    private boolean isSupportedExtension(String extension) {
        // 실제로는 Set<String>으로 관리하는 것이 더 효율적입니다.
        return extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jifi")
                ;
    }

}
