package com.kosa.jungdoin.member.controller;

import com.kosa.jungdoin.member.dto.BodyWeightLogDTO;
import com.kosa.jungdoin.member.service.BodyWeightLogService;
import com.kosa.jungdoin.member.service.MemberService;
import com.kosa.jungdoin.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BodyWeightLogService bodyWeightLogService;

    @GetMapping("/me")
    public ResponseEntity<Object> getContextUserInfo() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            resultMap.put("oauth", userDetails.getMemberOAuthId());
            resultMap.put("username", userDetails.getUsername());
            resultMap.put("role", userDetails.getRole());

            return ResponseEntity.ok(resultMap);
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        memberService.deleteAuthCookies(response);
        return ResponseEntity.ok("logout success");
    }

    @GetMapping("/private")
    public ResponseEntity<String> showPrivate() {
        return ResponseEntity.ok("success !!!");
    }

    @PostMapping("/body-weight")
    public ResponseEntity<Object> saveBodyWeight(@RequestBody BodyWeightLogDTO requestDTO) {
        try {
            BodyWeightLogDTO resultDTO = bodyWeightLogService.saveMemberBodyWeight(requestDTO);
            if (resultDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("동일한 날짜에는 하나의 데이터만 저장할 수 있습니다.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/body-weight/{memberId}")
    public ResponseEntity<Object> getBodyWeightLogs(@PathVariable Long memberId) {
        List<BodyWeightLogDTO> logs = bodyWeightLogService.getAllByMemberId(memberId);
        if (logs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(logs);
    }

    @PutMapping("/body-weight")
    public ResponseEntity<Object> updateBodyWeightLog(@RequestBody BodyWeightLogDTO requestDTO) {
        try {
            BodyWeightLogDTO resultDTO = bodyWeightLogService.update(requestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/body-weight")
    public ResponseEntity<Object> deleteBodyWeightLog(@RequestBody BodyWeightLogDTO requestDTO) {
        try {
            if (Boolean.FALSE.equals(bodyWeightLogService.removeLog(requestDTO))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
            }
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
