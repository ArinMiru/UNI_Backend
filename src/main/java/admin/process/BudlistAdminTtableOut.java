/*
 * BudlistAdminTtableOut.java -> 공지사항리스트 명세서를 참조 게시글 관리자 모니터링 페이지
 * 
 * JSON KEY
 * userId - 사용자 이름
 * userschool - 학교이름
 * userdep - 사용자 학과
 * budname - 게시글 제목
 * creTime - 작성시간
 * 
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

public class BudlistAdminTtableOut {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobj = new JSONObject();
	
	public BudlistAdminTtableOut (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml"; // 변경 예정
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			Map<String, Object> rtn = new HashMap<String, Object>();
			
			rtn = session.selectOne("uni-mapping.selectInfo",param);
			
			System.out.println(rtn.get("IND"));
			
			jobj.put("userId", param.get("userId"));
			jobj.put("userschool", param.get("userschool"));
			jobj.put("userdep", param.get("userdep"));
			jobj.put("budname", param.get("budname"));
			jobj.put("creTime", param.get("cretime"));
			
			
            JSONObject jo=new JSONObject();
			jo.put("FLAG", rtn.get("IND"));
			jo.put("FLAG1", rtn.get("NUM"));
			jary.add(jo);
		    
			//mybatis query file 작성 후 변경 예정
			
			jobj.put("TT_LIST", jary);

	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jobj;
	}

}