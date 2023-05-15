/*
 * 2023.05.09 김도원 <생성>
 * 
 * 2023.05.15 개발 담당 : 안재경
 * userInfo : 사용자 정보
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

public class UserInfoAdminTtableOut {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobj = new JSONObject();
	
	public UserInfoAdminTtableOut (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml"; // 변경 예정
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			Map<String, Object> rtn = new HashMap<String, Object>();
			
			rtn = session.selectOne("uni-mapping.selectInfo",param);
			
			System.out.println(rtn.get("IND"));
			
			jobj.put("userId", param.get("userId"));
			jobj.put("userType", param.get("userType"));
			jobj.put("userschool", param.get("userschool"));
			jobj.put("usersdep", param.get("usersdep"));
			
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