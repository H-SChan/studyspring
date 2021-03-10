package test.learnspring.repository;

import test.learnspring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);//영구저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {//PK는 이렇게 조회  가
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member as m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();//하나만 찾으면 되니 stream으로
    }

    @Override
    public List<Member> findAll() {

        return em.createQuery("select m from Member as m", Member.class).getResultList();
        //테이블 명을 엔티티명으로
    }
}
