package test.learnspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.learnspring.repository.JdbcMemberRepository;
import test.learnspring.repository.MemberRepository;
import test.learnspring.repository.MemoryMemberRepository;
import test.learnspring.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {//스프링 컨테이너에 올림
    
    private DataSource dataSource;
    
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }//DB쓸려고
    
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    
    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
