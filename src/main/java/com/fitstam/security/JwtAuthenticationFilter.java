package com.fitstam.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1.getToken
		
		String requestToken=request.getHeader("Authorization");
		System.out.println("               - "+requestToken);
		
		String userName=null;
		String actualToken=null;
		
		if(request != null && requestToken != null && requestToken.startsWith("Bearer")) {
			actualToken=requestToken.substring(7);
			try {
				userName=this.jwtTokenHelper.getUserNameFromToken(actualToken);
			} catch (IllegalArgumentException e) {
				System.out.println("unable to get JWT token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT token is Expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT token");
			}
			
		}else {
			System.out.println("Please enter token and try");
		}
		
		
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			
			if(userDetails !=null && this.jwtTokenHelper.validateToken(actualToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken filter= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				filter.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(filter);
				
				
			}else {
				System.out.println("Invalid JWT Token");
			}
			
		}else {
			System.out.println("username is null or context is not null");
		}
		filterChain.doFilter(request, response);
	}

}
