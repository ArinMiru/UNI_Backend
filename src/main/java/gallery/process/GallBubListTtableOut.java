package gallery.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GallBubListTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public GallBubListTtableOut (Map<String, Object> param) throws IOException {
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
					
			// 공지사항은 LIST 형태로 조회되기 때문에 LIST 선언
			List<Map<String, Object>> rtnList = null;			
					
			rtnList = session.selectList("uni-gallery-mapping.selectGallBubInfo",param);	
						
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
				JSONArray jarySub3 = new JSONArray();
				JSONObject jObjSub2 = new JSONObject();
				JSONObject jObjSub3 = new JSONObject();
				List<Map<String, Object>> rtnListSub2 = null;
				List<Map<String, Object>> rtnListSub3 = null;
							
				param.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
							
				// 공지사항이 loop 수행마다 해당 공지에 첨부된 이미지파일이 있을수 있으니 조회한다.
				rtnListSub2 = session.selectList("uni-gallery-mapping.selectGallImgInfo",param);	
				// 이미지 다건수만큼 loop 수행해서 json 만듦
				for (int j=0; j < rtnListSub2.size() ;j++)
				{
					jObjSub2.put("FILE_PATH", rtnListSub2.get(j).get("FILE_PATH"));
					jObjSub2.put("IMG_SEQ", rtnListSub2.get(j).get("IMG_SEQ"));
								
					jarySub2.add(jObjSub2);
				}
							
				jObjSub.put("IMAGE_INFO", jarySub2);
				
				// 공지사항이 loop 수행마다 해당 공지에 첨부된 이미지파일이 있을수 있으니 조회한다.
				rtnListSub3 = session.selectList("uni-gallery-mapping.selectGallAnsInfo",param);	
				// 이미지 다건수만큼 loop 수행해서 json 만듦
				for (int j=0; j < rtnListSub3.size() ;j++)
				{
					jObjSub3.put("ANS_MEMB_ID", rtnListSub3.get(j).get("ANS_MEMB_ID"));
					jObjSub3.put("ANS_SEQ", rtnListSub3.get(j).get("ANS_SEQ"));
					jObjSub3.put("CONT", rtnListSub3.get(j).get("CONT"));
					jObjSub3.put("CRE_DAT", rtnListSub3.get(j).get("CRE_DAT"));
											
					jarySub3.add(jObjSub3);
				}
										
				jObjSub.put("ANS_FREE", jarySub3);
				
					
				jarySub.add(jObjSub);
			}
					
			jObjMain.put("RSLT_CD", "00");
			jObjMain.put("GALL_BUB", jarySub);


		} catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD", "99");
		} finally {
			// 사용다한 세션 닫아주기
			if (session != null) session.close();    	
		}
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}