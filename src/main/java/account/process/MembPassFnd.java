/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)
 * MembPassFnd : 비밀번호 찾기
 * 
 */

/*
 * 결과코드 : RSLT_CD
 * 인증일련번호 : CERT_SEQ
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

public class MembPassFnd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembPassFnd (Map<String, Object> param) throws IOException {
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
			
			if (rtn == null) {
				rtn = new HashMap<String, Object>();
				rtn.put("RSLT_CD", "04"); // 04 : 이메일 정보없음
			} 
			jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
		
			
			if ("99".equals(rtn.get("RSLT_CD"))) {	
				// 99 : 기타오류
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			}			
			
			
			if ("00".equals(rtn.get("RSLT_CD"))) {
				// 00 : 정상 (코드 이메일로 정상 발송)
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
				jObjMain.put("CERT_SEQ", rtn.get("CERT_SEQ"));
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