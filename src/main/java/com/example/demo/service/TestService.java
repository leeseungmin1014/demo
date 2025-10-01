package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.TestDB;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;

@Service // 서비스 등록, 스프링 내부 자동 등록됨
@RequiredArgsConstructor // 생성자 생성
public class TestService {
    @Autowired // 객체 의존성 주입 DI(컨테이너 내부 등록)
    private TestRepository testRepository;

    public TestDB findByName(String name) { // 이름 찾기sss
        return (TestDB) testRepository.findByName(name);
    }
}