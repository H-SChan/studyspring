package test.learnspring.domain;

import javax.persistence.*;

@Entity//jpa
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//DB가 알아서 생성해 주는것을 IDENTITY라고 함

    private long id;
    //@Column(name = "username") DB에 name의 이름이 username이였다면 이렇게 해주어야 매핑이 됨
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}