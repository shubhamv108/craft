package code.shubham.commons.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class AspectUtils {

	public static <R extends Annotation> Optional<R> getClassAnnotation(final ProceedingJoinPoint joinPoint,
			final Class<R> clazz) {
		return Optional.of(joinPoint.getTarget().getClass().getAnnotationsByType(clazz)).map(roles -> roles[0]);
	}

	@SuppressWarnings("unchecked")
	public static <R> Optional<R> getMethodAnnotation(final ProceedingJoinPoint joinPoint, final Class<R> clazz) {
		for (final Annotation annotation : ((MethodSignature) joinPoint.getSignature()).getMethod()
			.getDeclaredAnnotations())
			if (annotation.getClass().equals(clazz))
				return Optional.of((R) annotation);
		return Optional.empty();
	}

}
