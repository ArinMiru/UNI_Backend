/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)
 * MembPassUpd : 비밀번호 변경
 * 
 */

/*
 * 
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

public class MembPassUpd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembPassUpd (Map<String, Object> param) throws IOException {
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
				// 00 : 정상 (코드 이메일로 정상 발송)
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