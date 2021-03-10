package test.learnspring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect//AOP룰 쑬슈 았게
@Component
public class TimeTraceAop {
    @Around("execution(* test.learnspring..*(..))")//test.learnspring패키지 아래의 클래스에 모두 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{//메서드 호출마다 실행
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());//어떤 매서드를 콜 하는지 알 수 있음
        try{
            Object result = joinPoint.proceed();//다음 메서드로 진행이 됨
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long time = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + time + "ms");
        }
    }
}
