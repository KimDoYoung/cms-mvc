package kr.dcos.common.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerMethod {
	String desc() default "";
	String alias() default ""; // 콤마로 다른 이름을 여러개 줄 수 있게 한다 ex: go,gohome,goindex
}
