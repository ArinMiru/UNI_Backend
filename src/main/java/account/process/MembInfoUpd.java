/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)
 * MembInfoUpd : 프로파일 이미지변경
 * 
 * 2023.05.10 김도원 수정 (uni-account-mapping.xml query 작성 및 try{} 코드 수정)
 * updateMembInfoUpd : 프로파일 이미지변경
 */

/*
 * 로그인결과코드 : RSLT_CD
 * 
 */

package account.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.tomcat.util.codec.binary.Base64;

import net.sf.json.JSONObject;

public class MembInfoUpd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembInfoUpd (JSONObject jobj) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
        try {
            
            // BASE64 문자열 읽기
			String fileBase = (jobj.get("FILE_BASE64") == null) ? "" : jobj.getString("FILE_BASE64");
            
            // uuid 고유식별자를 채번해서 파일명이 중복되지 않게 변경용
			UUID uuid = UUID.randomUUID();
			// 파일명 새로 구성
			// 추후 파일 저장 경로 변경 예정
			String saveFileNm = "C:\\UNI\\" + uuid + "_" + jobj.getString("MEMB_ID");
		
			// 이미지 BASE64 디코딩해서 파일로 생성
			byte decode[] = Base64.decodeBase64(fileBase);
			FileOutputStream fos;
			File target = new File(saveFileNm);
			target.createNewFile();
			fos = new FileOutputStream(target);
			fos.write(decode);
			fos.close();

			
			Map<String, Object> param = null;
			
			param.put("MEMB_ID", jobj.getString("MEMB_ID"));
			param.put("PROF_IMG_PATH", saveFileNm);

            // 수정된 부분: update 메소드를 사용하도록 변경
            int updatedRows = session.update("uni-account-mapping.updateMembInfoUpd",param);
            Map<String, Object> rtn = new HashMap<String, Object>();

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