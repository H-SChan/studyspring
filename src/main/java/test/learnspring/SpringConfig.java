package test.learnspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.learnspring.aop.TimeTraceAop;
import test.learnspring.repository.JpaMemberRepository;
import test.learnspring.repository.MemberRepository;
import test.learnspring.service.MemberService;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {//스프링 컨테이너에 올림
    
    private DataSource dataSource;
    private EntityManager em;
    private final MemberRepository memberRepository;
    
    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em, MemberRepository memberRepository) {//DB쓸려고
        this.dataSource = dataSource;
        this.em = em;
        this.memberRepository = memberRepository;
    }
    
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
        //return new MemberService(memberRepository);//spring boot JPA에서
    }

    
    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
