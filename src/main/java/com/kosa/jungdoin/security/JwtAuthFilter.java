package com.kosa.jungdoin.security;

import com.kosa.jungdoin.common.CookieManager;
import com.kosa.jungdoin.common.exception.CustomException;
import com.kosa.jungdoin.common.jwt.JwtProvider;
import com.kosa.jungdoin.common.jwt.service.JwtRefreshService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.kosa.jungdoin.common.CookieManager.ACCESS_TOKEN;
import static com.kosa.jungdoin.common.CookieManager.REFRESH_TOKEN;
import static com.kosa.jungdoin.common.exception.CustomExceptionCode.EXPIRED_JWT;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final CustomUserDetailsService userDetailsService;
	private final CookieManager cookieManager;
	private final JwtRefreshService jwtRefreshService;

	// 필터 검증 예외 처리
	// shouldNotFilter()는 OncePerRequestFilter의 상위 클래스에 정의된 메서드, 필터로 체크하지 않을 경로나 메서드 등을 지정하기 위해서 사용
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		List<String> excludePatterns;

		// Preflight(Ajax 통신 실제 요청을 보내기 전에 OPTIONS 메서드를 사용하여 서버에 대한 정보를 요청) 요청은 체크하지 않음
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}

		if (request.getMethod().equals("GET")) {
			excludePatterns = List.of(
					"/api/jwt/refresh"
			);
			return excludePatterns.stream().anyMatch(path::startsWith);
		}

		if (request.getMethod().equals("POST")) {
			excludePatterns = List.of(

			);
			return excludePatterns.stream().anyMatch(path::startsWith);
		}

		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = cookieManager.getCookie(request, ACCESS_TOKEN);
		String memberOAuthId = null;

		if (token != null) {
			try {
				jwtProvider.isTokenExpired(token);
			} catch (CustomException e) {
				//토큰이 파기되었을 때 리프레시 토큰을 통해 액세스 토큰을 재발급
				if (e.getExceptionCode().equals(EXPIRED_JWT)) {
					Map<String, String> tokens = jwtRefreshService.refreshToken(request);
					cookieManager.deleteCookie(response, ACCESS_TOKEN);
					cookieManager.deleteCookie(response, REFRESH_TOKEN);
					cookieManager.setSecureHttpOnlyCookie(response, tokens.get(ACCESS_TOKEN), ACCESS_TOKEN,
						jwtProvider.getRefreshTokenExpirationPeriod());
					cookieManager.setSecureHttpOnlyCookie(response, tokens.get(REFRESH_TOKEN), REFRESH_TOKEN,
						jwtProvider.getRefreshTokenExpirationPeriod());

					token = tokens.get(ACCESS_TOKEN);
				}
			}

			Map<String, Object> claims = (Map<String, Object>)jwtProvider.getClaims(token).get("body");
			String provider = (String) claims.get("provider");
			String id = (String) claims.get("id");
			memberOAuthId = provider + "_" + id;
			if (!provider.isEmpty() && !id.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(memberOAuthId);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}