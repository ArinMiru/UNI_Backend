/*
 * LonginTtableOut : 로그인_공통
 * 
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class LonginTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public LonginTtableOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// mybatis-config.xml 을 이용하여 db 커넥션 생성
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
		
			
			// sql 호출 결과를 단건으로 받아오기 위한 map 선언 (조회용 key,value )
			Map<String, Object> rtn = null;
			
			System.out.println("param :"+param.toString());
			
			if (param.get("TOKEN_ID").toString().isEmpty())
			{
				// 로그인 검증 SQL 호출
				rtn = session.selectOne("uni-account-mapping.selectCheckId",param);
			} else {
				rtn = session.selectOne("uni-account-mapping.tokenCheckId",param);
				param.put("LOGIN_ID", rtn.get("LOGIN_ID"));
			}
			
			// 로그인결과코드 JSON MAIN 항목추가
			if (rtn == null) {
				rtn = new HashMap<String, Object>();
				rtn.put("RSLT_CD", "01");
			} 
			
		
			// 로그인결과코드 JSON MAIN 항목추가
			jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			
			//---------------------------------------------------------
			
			Map<String, Object> rtn1 = null;
			// 로그인 결과가 정상인경우 회원정보 조회
			if ("00".equals(rtn.get("RSLT_CD"))) {
				rtn1 = session.selectOne("uni-account-mapping.selectMembInfo",param);
				// 회원정보를 JSON MAIN 항목추가				
				jObjMain.put("LOGIN_ID", rtn1.get("LOGIN_ID"));
				jObjMain.put("MEMB_NM", rtn1.get("MEMB_NM"));
				jObjMain.put("MEMB_SC_CD", rtn1.get("MEMB_SC_CD"));
				jObjMain.put("MEMB_DEP_CD", rtn1.get("MEMB_DEP_CD"));
				jObjMain.put("MEMB_NUM", rtn1.get("MEMB_NUM"));
				jObjMain.put("MEMB_SC_NM", rtn1.get("MEMB_SC_NM"));
				jObjMain.put("MEMB_DEP_NM", rtn1.get("MEMB_DEP_NM"));	
				jObjMain.put("TIT_CD", rtn1.get("TIT_CD"));
				jObjMain.put("TIT_NM", rtn1.get("TIT_NM"));
				jObjMain.put("NICK_NM", rtn1.get("NICK_NM"));
				jObjMain.put("MEMB_GRA", rtn1.get("MEMB_GRA"));
				jObjMain.put("MEMB_EM", rtn1.get("MEMB_EM"));
				jObjMain.put("PROF_IMG_PATH", rtn1.get("PROF_IMG_PATH"));
				jObjMain.put("COLL_CERT_IND", rtn1.get("COLL_CERT_IND"));
				
				UUID uuid = UUID.randomUUID();
				
				String token = rtn1.get("LOGIN_ID").toString();
				// 토큰 구성
				token = token + uuid;
				
				jObjMain.put("TOKEN_ID", token);
				
				rtn1.put("TOKEN_ID", token);
				//rtn1.put("LOGIN_ID", rtn1.get("LOGIN_ID"));
				// 토큰 등록
				session.insert("uni-account-mapping.insertTokenInfo",rtn1);	
				
			}

	    } catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD", "99");
	    } finally {
	    	// 사용다한 세션 닫아주기
	    	if (session != null) session.close();  
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}