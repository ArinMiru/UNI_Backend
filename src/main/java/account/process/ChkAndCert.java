/*
 * 2023.05.10 김도원 생성 (API 상세명세서 기반 개발)
 * ChkAndCert : 코드검증 및 인증완료
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
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class ChkAndCert {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public ChkAndCert (Map<String, Object> param) throws IOException {
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

            System.out.println("param :"+param.toString());

            // 수정된 부분: update 메소드를 사용하도록 변경
            int updatedRows = session.update("uni-account-mapping.**",param);
            rtn = new HashMap<String, Object>();

            if (updatedRows == 0) {
                rtn.put("RSLT_CD", "00"); // 00: 정상
                jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
                jObjMain.put("MEMB_ID", rtn.get("MEMB_ID"));
            } else if (updatedRows > 0){
                rtn.put("RSLT_CD", "05"); // 05: 인증코드불일치
                jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
            } else {
                rtn.put("RSLT_CD", "99"); // 99: 기타 오류
                jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
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