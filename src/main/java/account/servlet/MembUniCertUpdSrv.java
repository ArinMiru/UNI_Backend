/*
 * 대학인증 회원수정 부분 수정
 * userId : 회원 아이디
 * userSccd : 학교코드
 * userDepcd : 학과코드
 * userNum : 학번
 * userEm : 이메일
 */
package account.process;

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

public class MembUniCertUpd {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobj = new JSONObject();
	
	public MembUniCertUpd (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占썩본 占쏙옙占쏙옙 占쏙옙占쏙옙
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			//pstmt.setString(1, userPasswrd);
			//pstmt.setString(2, userId);
			//pstmt.setString(3, userType);
			
			Map<String, Object> rtn = new HashMap<String, Object>();
			
			rtn = session.selectOne("uni-mapping.selectInfo",param);
			
			System.out.println(rtn.get("IND"));
			
			// rsltCd 占쌓몌옙 占쌩곤옙
			// 占시뤄옙占쏙옙占쏙옙占쏙옙 JSON 占쌓몌옙 占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
			//jobj.put("FLAG", rtn.get("IND"));
			// userId 요청변수
			jobj.put("userId", param.get("userId"));
			// userType 요청변수
			jobj.put("userSccd", param.get("userSccd"));
			// userDepcd 요청변수
			jobj.put("userDepcd", param.get("userDepcd"));
			// userNum 요청변수
			jobj.put("userNum", param.get("userNum"));
			// userEm 요청변수
			jobj.put("userEm", param.get("userEm"));

			// 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싼곤옙 占쌥억옙占쌍쏙옙
			
            JSONObject jo=new JSONObject();
			jo.put("FLAG", rtn.get("IND"));
			jo.put("FLAG1", rtn.get("NUM"));
			jary.add(jo);
		    
			// 占시곤옙표占쏙옙 TT_LIST 占쏙옙占� 占쏙옙표占쏙옙占쏙옙占쏙옙 占썼열 占쏙옙占승뤄옙 占쏙옙占쏙옙
			jobj.put("TT_LIST", jary);

	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		// 占쏙옙占쏙옙占쏙옙占� 占쏙옙占쏙옙
		return jobj;
	}

}