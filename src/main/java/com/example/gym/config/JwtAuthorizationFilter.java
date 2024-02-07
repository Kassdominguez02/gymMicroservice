package com.example.gym.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;


@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      try {
        String jwt = extractTokenFromRequest(request);
        if (jwt != null && jwtTokenUtil.validateToken(jwt)) {
          String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

          UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null, // ES NULL PORQUE SERIA LA CONTRASEÑA, Y YA ESTA ENCRIPTADA
                  userDetails.getAuthorities()); // OBTIENE LOS PERMISOS O ROLES
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication); // AQUI SE GUARDA LA AUTENTICACION Y NECESITO OBTENER EL CONTEXTO
        }
      } catch (Exception e) {
        logger.error("Cannot set user authentication: {}", e);
      }

      filterChain.doFilter(request, response);
    }

    
    private String extractTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
          return headerAuth.substring(7);
        }

        return null;
      }

}
