package com.example.demo.Controller;

import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.example.demo.model.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.service.AddArticleRequest;
// import com.example.demo.model.domain.TestDB;
import com.example.demo.service.BlogService; // 최상단 서비스 클래스 연동 추가

import jakarta.servlet.http.HttpSession;

@Controller // 컨트롤러 어노테이션 명시
public class FileController // 클래스 선언부 가정
{
    @Autowired
    // BlogService blogService; // 주석 처리된 상태로 유지

    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
    private String uploadFolder;

    /**
     * 이메일, 제목, 메시지를 텍스트 파일로 저장하는 요청을 처리합니다.
     * * @param email 사용자 이메일 (파일 이름 생성에 사용)
     * 
     * @param subject            메시지 제목
     * @param message            메시지 본문
     * @param redirectAttributes 리다이렉트 후 메시지 전달을 위한 Flash 속성
     * @return 성공 시 'upload_end' 뷰 이름, 오류 시 '/error_page/article_error' 뷰 이름
     */
    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. 업로드 경로 확인 및 생성
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 2. 파일 이름 정리 및 경로 설정
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt"); // 업로드 폴더에 .txt 이름 설정

            System.out.println("File path: " + filePath); // 디버깅용 출력

            // 3. 파일 내용 작성
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일 제목: " + subject); // 쓰기
                writer.newLine(); // 줄 바꿈
                writer.write("요청 메시지:");
                writer.newLine();
                writer.write(message);
            }

            // 4. 성공 메시지 설정
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");

        } catch (IOException e) {
            // 파일 처리 중 오류 발생 시 처리 (사용자 요청 반영)
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
            return "/error_page/article_error"; // 오류 처리 페이지로 연결 (사용자 요청 반영)
        }

        // 성공 시 'upload_end.html' 또는 'upload_end.jsp' 뷰를 렌더링 (사용자 요청 반영)
        return "upload_end";
    }
}