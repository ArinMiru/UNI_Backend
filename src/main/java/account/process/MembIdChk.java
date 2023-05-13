/*
 * 2023.05.09 김도원 생성 (API 상세명세서 기반 개발)
 * MembIdChk : 회원 ID 중복체크
 * 
 * 2023.05.10 김도원 수정 (uni-account-mapping.xml query 작성 및 try{} 코드 수정)
 * selectMembIdchk : 회원 ID 중복 체크
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

public class MembIdChk {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembIdChk (Map<String, Object> param) throws IOException {
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

            // 수정된 부분: update 메소드를 사용하도록 변경
            int updatedRows = session.update("uni-account-mapping.selectMembIdchk",param);
            rtn = new HashMap<String, Object>();

            if (updatedRows == 0) {
                rtn.put("RSLT_CD", "00"); // 00: 정상
            } else if (updatedRows > 0){
                rtn.put("RSLT_CD", "03"); // 03: 중복된 ID
            } else {
                rtn.put("RSLT_CD", "99"); // 99: 기타 오류
            }
            
            jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}