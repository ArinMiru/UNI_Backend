/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)\
 * MembUniCertUpd : 대학인증 회원수정
 * 
 * 2023.05.10 김도원 수정 (uni-account-mapping.xml query 작성 및 try{} 코드 수정)
 * updateMembuniCertUpd : 대학인증 회원수정
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

public class MembUniCertUpd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembUniCertUpd (Map<String, Object> param) throws IOException {
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
            int updatedRows = session.update("uni-account-mapping.updateMembuniCertUpd",param);
            rtn = new HashMap<String, Object>();

            if (updatedRows > 0) {
                rtn.put("RSLT_CD", "00"); // 00: 정상
            } else {
                rtn.put("RSLT_CD", "99"); // 99: 기타 오류
            }

            jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
            
            session.commit();

			
	    } catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD","99");
	    } finally {
	    	// 사용다한 세션 닫아주기
	    	if (session != null) session.close();
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}