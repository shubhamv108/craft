package code.shubham.commons.aspects;

import code.shubham.commons.annotations.Role;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.exceptions.UnauthorizedException;
import code.shubham.commons.utils.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class RoleAspect {

	@Around("execution(* (@code.shubham.commons.annotations.Role *).*(..)) || execution(@code.shubham.commons.annotations.Role * *(..))")
	public Object process(final ProceedingJoinPoint joinPoint) throws Throwable {
		this.validateAccess(AspectUtils.getClassAnnotation(joinPoint, Role.class));
		this.validateAccess(AspectUtils.getMethodAnnotation(joinPoint, Role.class));
		return joinPoint.proceed();
	}

	private void validateAccess(final Optional<Role> role) {
		if (role.isPresent() && !RoleContextHolder.has(role.get().value()))
			throw new UnauthorizedException();
	}

}