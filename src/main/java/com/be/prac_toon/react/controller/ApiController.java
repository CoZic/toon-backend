package com.be.prac_toon.react.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    /*
        // React App.js에서 이 API를 호출하는 예시입니다.
        function App() {
            const [message, setMessage] = useState('');
            useEffect(() => {
                axios.get('/api/hello')  <== 호출 URL 설정
     */
    // HTTP GET 요청이 "/api/hello" 경로로 왔을 때 이 메소드를 실행하라고 매핑합니다.
    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        // Java의 Map 객체를 생성합니다.
        Map<String, String> response = new HashMap<>();

        // "message" 라는 키에 원하는 문자열 값을 담습니다.
        response.put("message", "안녕하세요! Spring Boot 백엔드에서 보낸 메시지입니다! 🎉");

        // Map 객체를 반환합니다. Spring Boot가 자동으로 이 Map을 JSON 형식으로 변환해 응답합니다.
        // 예: {"message": "안녕하세요! ..."}
        return response;
    }

}
