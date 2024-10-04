package com.kosa.jungdoin.member.controller;

import com.kosa.jungdoin.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        memberService.deleteAuthCookies(response);
        return ResponseEntity.ok("logout success");
    }

    @GetMapping("/private")
    public ResponseEntity<String> showPrivate() {
        return ResponseEntity.ok("success !!!");
    }

}
