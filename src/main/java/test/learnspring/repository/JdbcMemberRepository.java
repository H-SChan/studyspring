package test.learnspring.repository;

import test.learnspring.domain.Member;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;
    
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Member save(Member member) {
        /*String sql = "insert into member(name) values(?)";
    
        Connection conn = dataSource.getConnection();//DB커넥션을 가지고 옴
    
        PreparedStatement pstmt = conn.prepareStatement(sql);//문장 작성
        pstmt.setString(1, member.getName());
    
        pstmt.executeUpdate();
    
        return null;
    
        이러면 대충 쿼리가 DB로 날라감*/
        
        String sql = "insert into member(name) values(?)";//sql을 만들어둠 그냥 상수로 쓰는게 나음
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;//결과를 받음
        
        try {
            conn = getConnection();//커넥션 가져오기
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//sql변수를 넣고, RETURN_GENERATED_KEYS속성은 insert해서 id를 알아와야 사용가능
            
            pstmt.setString(1, member.getName());//1은 sql변수의 values의 ?와 매칭됨, member.getName()으로 값을 넣음
            
            pstmt.executeUpdate();//쿼리가 날라감
            rs = pstmt.getGeneratedKeys();//RETURN_GENERATED_KEYS옵션과 매칭되어서 사용(ID 반환)
            
            if (rs.next()) {//값이 있으면
                member.setId(rs.getLong(1));//getLong로 값을 꺼내서 세팅
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);//끝나면 다 닫기
        }
    }
    
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";//조회할 쿼리문
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            
            rs = pstmt.executeQuery();//조회
            
            if (rs.next()) {//만약 값이 있다면
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);//맴버 객체 생성후 반환
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            List<Member> members = new ArrayList<>();//모두 담기 위해서 리스트
            
            while (rs.next()) {//만약 있다면
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);//모두 담음
            }
            return members;//리턴
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();//없다면 대충 리턴
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}