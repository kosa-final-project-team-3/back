package com.kosa.jungdoin.common.jwt;

import com.kosa.jungdoin.common.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.kosa.jungdoin.common.exception.CustomExceptionCode.EXPIRED_JWT;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class JwtProvider {

	public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String BEARER = "Bearer ";
	private SecretKey cachedSecretKey;
	@Value("${custom.jwt.secretKey}")
	private String secretKeyPlain;
	@Value("${custom.jwt.access.expiration}")
	private Long accessTokenExpirationPeriod;
	@Value("${custom.jwt.refresh.expiration}")
	private Long refreshTokenExpirationPeriod;
	@Value("${custom.jwt.access.header}")
	private String accessHeader;
	@Value("${custom.jwt.refresh.header}")
	private String refreshHeader;

	private SecretKey _getSecretKey() {
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
	}

	public SecretKey getSecretKey() {
		if (cachedSecretKey == null)
			cachedSecretKey = _getSecretKey();

		return cachedSecretKey;
	}

	/**
	 * AccessToken 생성 메소드
	 */
	public String createAccessToken(Map<String, Object> claims) {
		Date now = new Date();

		return Jwts.builder()
			.subject(ACCESS_TOKEN_SUBJECT)
			.claim("body", claims)
			.expiration(new Date(now.getTime() + accessTokenExpirationPeriod))
			.signWith(getSecretKey(), Jwts.SIG.HS512)
			.compact();
	}

	/**
	 * RefreshToken 생성
	 */
	public String createRefreshToken() {
		Date now = new Date();
		return Jwts.builder()
			.subject(REFRESH_TOKEN_SUBJECT)
			.expiration(new Date(now.getTime() + refreshTokenExpirationPeriod))
			.signWith(getSecretKey(), Jwts.SIG.HS512)
			.compact();
	}

	/**
	 * AccessToken 헤더에 실어서 보내기
	 */
	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		response.setHeader(accessHeader, accessToken);
		log.info("재발급된 Access Token : {}", accessToken);
	}

	/**
	 * AccessToken + RefreshToken 헤더에 실어서 보내기
	 */
	public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		setAccessTokenHeader(response, accessToken);
		setRefreshTokenHeader(response, refreshToken);
		log.info("Access Token, Refresh Token 헤더 설정 완료");
	}

	/**
	 * 헤더에서 RefreshToken 추출
	 * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
	 * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
	 */
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(refreshHeader))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * 헤더에서 AccessToken 추출
	 * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
	 * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
	 */
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessHeader))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * AccessToken에서 Email 추출
	 * 추출 전에 JWT.require()로 검증기 생성
	 * verify로 AceessToken 검증 후
	 * 유효하다면 getClaim()으로 이메일 추출
	 * 유효하지 않다면 빈 Optional 객체 반환
	 */
	public Map<String, Object> getClaims(String accessToken) {
		try {
			return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(accessToken)
				.getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (Exception e) {
			return Collections.emptyMap();
		}
	}

	/**
	 * AccessToken 헤더 설정
	 */
	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(accessHeader, accessToken);
	}

	/**
	 * RefreshToken 헤더 설정
	 */
	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(refreshHeader, refreshToken);
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void isTokenExpired(String token) {
		try {
			Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token);
		} catch (ExpiredJwtException e) {
			throw new CustomException(EXPIRED_JWT);
		}
	}
}