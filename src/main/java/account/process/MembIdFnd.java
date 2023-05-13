/*
 * 2023.05.09 김도원 생성 (API 상세명세서 기반 개발)
 * MembIdFnd : 아이디찾기
 * 
 * 2023.05.12 김도원 session commit, close 코드 작성 및 미사용 import 제거
 * 
 */

/*
 * 
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class MembIdFnd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembIdFnd (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		Map<String, Object> rtn = null;
		
		try {
			System.out.println("param :"+param.toString());
			
			rtn = session.selectOne("uni-account-mapping.**",param);
			
			
			if ("99".equals(rtn.get("RSLT_CD"))) {	
				// 99 : 기타오류
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			}			
			
			if ("00".equals(rtn.get("RSLT_CD"))) {
				// 00 : 정상 (코드 이메일로 정상 발송)
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
				jObjMain.put("CERT_SEQ", rtn.get("CERT_SEQ"));
			}
			
			session.commit();
			
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
			if (session != null) session.close();	
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}