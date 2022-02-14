package com.salesianostriana.dam.Miarma.security.jwt;


import com.salesianostriana.dam.Miarma.users.model.UserEntity;
import com.salesianostriana.dam.Miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserEntityService userEntityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);

        try{
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
                UUID userId = jwtProvider.getUserIdFromJwt(token);

                Optional<UserEntity> userEntity = userEntityService.findById(userId);

                if (userEntity.isPresent()){
                    UserEntity user = userEntity.get();
                    UsernamePasswordAuthenticationToken authenticationFilter =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    user.getAuthorities()
                            );
                    authenticationFilter.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
                }
            }
        } catch (Exception ex) {
            log.info("No se pudo establecer el contexto de seguridad " +ex.getMessage());
        }
        filterChain.doFilter(request,response);
    }

    public String getJwtFromRequest( HttpServletRequest request){
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)){
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }
        return null;
    }
}