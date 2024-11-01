package com.rubaks.timetrackerapp.security.auditing;

import com.rubaks.timetrackerapp.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    // Класс реализует интерфейс AuditorAware и предоставляет информацию о текущем аудиторе.
    // Метод getCurrentAuditor возвращает ID текущего аутентифицированного пользователя,
    // который будет использоваться для записи информации об аудите в приложении.
    // Если пользователь не аутентифицирован или является анонимным, возвращается пустое значение.
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        User userPrincipal = (User) authentication.getPrincipal();

        return Optional.ofNullable(userPrincipal.getId()).map(Long::intValue);
    }
}
