/*
 * BudlistAdminDetail.java -> 게시글관리자 상세페이지 api
 * 
 * JSON KEY
 * userId - 사용자 아이디
 * username - 사용자 닉네임
 * userschool - 학교이름
 * userdep - 학과이름
 * budname - 게시글 제목
 * cretime - 작성시간
 * budcontent - 게시글 내용
 * 
 */

package admin.process;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BudlistAdminDetail {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobj = new JSONObject();
	
	public BudlistAdminDetail (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml"; // 변경 예정
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			Map<String, Object> rtn = new HashMap<String, Object>();
			
			rtn = session.selectOne("uni-mapping.selectInfo",param);
			
			System.out.println(rtn.get("IND"));
			
			jobj.put("userId", param.get("userId"));
			jobj.put("username", param.get("username"));
			jobj.put("userschool", param.get("userschool"));
			jobj.put("userdep", param.get("userdep"));
			jobj.put("budname", param.get("budname"));
			jobj.put("cretime", param.get("cretime"));
			jobj.put("budcontent", param.get("budcontent"));
			
			
            JSONObject jo=new JSONObject();
			jo.put("FLAG", rtn.get("IND"));
			jo.put("FLAG1", rtn.get("NUM"));
			jary.add(jo);
		    
			//관련정보를 select -> 배열로 출력
			
			jobj.put("TT_LIST", jary);

	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		// ������� ����
		return jobj;
	}

}