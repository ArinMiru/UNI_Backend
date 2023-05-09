/*
 * 2023.05.09 김도원 생성 (API 상세명세서 기반 개발)
 * MembNicChk : 닉네임 중복 체크
 * 
 */

/*
 * 
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class MembNicChk {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembNicChk (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			Map<String, Object> rtn = null;
			
			System.out.println("param :"+param.toString());
			
			rtn = session.selectOne("uni-account-mapping.**",param);
			
			
			if ("99".equals(rtn.get("RSLT_CD"))) {	
				// 99 : 기타오류
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			}			
			
			if ("00".equals(rtn.get("RSLT_CD"))) {
				// 00 : 정상
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			}
			
			if ("06".equals(rtn.get("RSLT_CD"))) {
				// 06 : 닉네임 중복
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			}
			
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}