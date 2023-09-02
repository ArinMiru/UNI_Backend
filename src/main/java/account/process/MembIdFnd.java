/*
 * 2023.05.09 김도원 생성 (API 상세명세서 기반 개발)
 * MembIdFnd : 아이디찾기
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
		
		try {
			Map<String, Object> rtn = null;
			rtn = session.selectOne("uni-account-mapping.selectIdEmail",param);
			
			// 로그인결과코드 JSON MAIN 항목추가
			if (rtn == null) {
				jObjMain.put("RSLT_CD","99");
			} 
			
			jObjMain.put("RSLT_CD",rtn.get("RSLT_CD"));
			jObjMain.put("MEMB_ID",rtn.get("MEMB_ID"));
			
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