package test.learnspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.learnspring.domain.Member;

import java.util.Optional;

//interface가 interface를 상속 받을 때에는 extends
//<Entity의 타입클래스, PK값의 type>
//구현체를 만들어 스프링빈에 자동으로 등록해
public interface SpringDataJpaRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
}
