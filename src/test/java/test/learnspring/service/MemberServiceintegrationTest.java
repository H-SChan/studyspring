package test.learnspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import test.learnspring.domain.Member;
import test.learnspring.repository.MemberRepository;
import test.learnspring.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest//스프링 컨테이너와 테스트를 함께 실행시킴
@Transactional//실행이 끝나면 DB를 롤백시킴(테스트케이스에서만)
class MemberServiceintegrationTest {
    
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("Spring");
        
        //when
        Long saveId = memberService.join(member);//setName한 member를 memberService에 넣어 saveId변수에 저장
        
        //then
        Member findMember = memberService.findOne(saveId).get();//memberService에 아까 저장한 이름을 찾아 가져와 findMember변수에 저장
        assertThat(member.getName()).isEqualTo(findMember.getName());//member의 이름과 findMember의 이름이 일치한지
    }
    
    @Test
    public void 중복_회원_가입() {
        //given
        Member member1 = new Member();
        member1.setName("Spring");
        
        Member member2 = new Member();
        member2.setName("Spring");
        
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //memberService의 join에 member2를 넣었을 때 IllegalStateException.class가 발생해야함
        
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");//IllegalStateException에 들어있는 메세지가 "이미 존재하는 회원입니다."과 같다면 통과
    }
}