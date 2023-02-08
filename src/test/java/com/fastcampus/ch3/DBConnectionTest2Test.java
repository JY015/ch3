package com.fastcampus.ch3;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class) //ac를 자동으로 만들어줌
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test{
    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception{
        User user = new User("asdf1","1234","abc","aaa@aaa.com",new Date(),"fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    @Test
    public void UpdateUserTest() throws Exception{
        User user = new User("asdf1","1234","abc","aaa@aaa.com",new Date(),"fb", new Date());
        deleteAll();
        int rowCnt = updateUser(user);

        assertTrue(rowCnt==1);

    }

    @Test
    public void selectUserTest() throws Exception{
        deleteAll();
        User user = new User("asdf1","1234","abc","aaa@aaa.com",new Date(),"fb", new Date());
        int rowCnt = insertUser(user);

        User user2 = selectUser("asdf1");

        assertTrue(user.getId().equals("asdf1"));
    }

    @Test
    public void deleteUserTest() throws Exception{
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt==0);

        User user = new User("asdf1","1234","abc","aaa@aaa.com",new Date(),"fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1);

        assertTrue(selectUser(user.getId())==null);

    }
    public int updateUser(User user) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "update user_info set pwd=?, name=?, email=?, birth=?, sns = ?, reg_date=? where id= ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(5, user.getSns());
        pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));
        pstmt.setString(7, user.getId());

//        int rowCnt = pstmt.executeUpdate();
//        return rowCnt;
        int rowCount = pstmt.executeUpdate();
        return rowCount;
    }
    public int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
//        int rowCnt = pstmt.executeUpdate();
//        return rowCnt;
        return pstmt.executeUpdate();
    }

    public User selectUser(String id) throws Exception{
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id=?";


        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery(); // insert, delete, update

        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;

        }
        return null;

    };

    private void deleteAll() throws Exception{
        Connection conn = ds.getConnection();

        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.executeUpdate(); // insert, delete, update

    }
    @Test
    public void transactionTest() throws Exception{
        Connection conn = null;
        try {
            deleteAll();
            conn = ds.getConnection();
            conn.setAutoCommit(false);

            String sql = " insert into user_info values(?,?,?,?,?,?,now())";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "asdf");
            pstmt.setString(2, "1234");
            pstmt.setString(3, "aa");
            pstmt.setString(4, "1234@aa.com");
            pstmt.setDate(5, new java.sql.Date(new Date().getTime()));
            pstmt.setString(6, "fb");

            int rowCnt = pstmt.executeUpdate(); // insert, delete, update

            pstmt.setString(1, "asdf2");
            rowCnt = pstmt.executeUpdate(); // insert, delete, update

            pstmt.setString(1, "asdf3");
            pstmt.setString(3, "무식");
            rowCnt = pstmt.executeUpdate(); // insert, delete, update

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {

        }

    }
    // 사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception {
        Connection conn = ds.getConnection();

//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('aaa','1234','aa','aaa@ab.com','2021-01-01','facebook',now());

        String sql = " insert into user_info values(?,?,?,?,?,?,now())";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate(); // insert, delete, update


        return rowCnt;

    }

    @Test
    public void springJdbcConnectTest() throws Exception{
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null);
    }
}