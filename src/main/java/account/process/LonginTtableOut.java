/*
 * 최초 생성 hapje000
 * 
 * 2023.05.12 김도원 session commit, close 코드 작성 및 미사용 import 제거
 * 
 */

/*
 * LonginTtableOut : 로그인_공통
 * 
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
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
			
			// 로그인 검증 SQL 호출
			rtn = session.selectOne("uni-account-mapping.selectCheckId",param);
			
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
			}
			
			// 로그인 결과가 정상인경우 공지게시판 조회
			if ("00".equals(rtn.get("RSLT_CD"))) {
				// 공지사항은 LIST 형태로 조회되기 때문에 LIST 선언
				List<Map<String, Object>> rtnList = null;
				
				param.put("MEMB_SC_CD", rtn1.get("MEMB_SC_CD"));
				param.put("MEMB_DEP_CD", rtn1.get("MEMB_DEP_CD"));
				
				rtnList = session.selectList("uni-account-mapping.selectOpenBubInfo",param);	
				
				JSONArray jarySub = new JSONArray();
				JSONObject jObjSub = new JSONObject();
				
				
				// 공지사항은 다건일수 있기에 selectList 로 호출하며 loop 를 수행하여 레코드별 JSON 형태로 만들어준다
				for (int i=0; i < rtnList.size() ;i++)
				{
					jObjSub.put("MEMB_ID", rtnList.get(i).get("MEMB_ID"));
					jObjSub.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
					jObjSub.put("TIT", rtnList.get(i).get("TIT"));
					jObjSub.put("CONT", rtnList.get(i).get("CONT"));
					jObjSub.put("LIKE_CNT", rtnList.get(i).get("LIKE_CNT"));
					jObjSub.put("CRE_DAT", rtnList.get(i).get("CRE_DAT"));
					
					JSONArray jarySub2 = new JSONArray();
					JSONObject jObjSub2 = new JSONObject();
					List<Map<String, Object>> rtnListSub2 = null;
					
					param.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
					
					// 공지사항이 loop 수행마다 해당 공지에 첨부된 이미지파일이 있을수 있으니 조회한다.
					rtnListSub2 = session.selectList("uni-account-mapping.selectOpenImgInfo",param);	
					// 이미지 다건수만큼 loop 수행해서 json 만듦
					for (int j=0; j < rtnListSub2.size() ;j++)
					{
						jObjSub2.put("FILE_PATH", rtnListSub2.get(j).get("FILE_PATH"));
						jObjSub2.put("IMG_SEQ", rtnListSub2.get(j).get("IMG_SEQ"));
						
						jarySub2.add(jObjSub2);
					}
					
					jObjSub.put("IMAGE_INFO", jarySub2);
					
					jarySub.add(jObjSub);
				}
				
				jObjMain.put("OPEN_BUB", jarySub);
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