package community.free.process;

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

public class FreeBubListTtableOut {    
	
	private JSONObject jobj = new JSONObject();
	
	public FreeBubListTtableOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// mybatis-config.xml 을 이용하여 db 커넥션 생성
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			
			// 공지사항은 LIST 형태로 조회되기 때문에 LIST 선언
			List<Map<String, Object>> rtnList = null;			
								
			rtnList = session.selectList("uni-community-mapping.selectFreeBubInfo",param);	
									
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
				
				jObjSub.put("MEMB_SC_NM", rtnList.get(i).get("MEMB_SC_NM"));
				jObjSub.put("MEMB_DEP_NM", rtnList.get(i).get("MEMB_DEP_NM"));
				jObjSub.put("TIT_NM", rtnList.get(i).get("TIT_NM"));
				jObjSub.put("NICK_NM", rtnList.get(i).get("NICK_NM"));
										
				JSONArray jarySub2 = new JSONArray();
				JSONObject jObjSub2 = new JSONObject();
				List<Map<String, Object>> rtnListSub2 = null;
										
				param.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
										
				// 공지사항이 loop 수행마다 해당 공지에 첨부된 이미지파일이 있을수 있으니 조회한다.
				rtnListSub2 = session.selectList("uni-community-mapping.selectFreeAnsInfo",param);	
				// 이미지 다건수만큼 loop 수행해서 json 만듦
				for (int j=0; j < rtnListSub2.size() ;j++)
				{
					jObjSub2.put("ANS_MEMB_ID", rtnListSub2.get(j).get("ANS_MEMB_ID"));
					jObjSub2.put("ANS_SEQ", rtnListSub2.get(j).get("ANS_SEQ"));
					jObjSub2.put("CONT", rtnListSub2.get(j).get("CONT"));
					jObjSub2.put("CRE_DAT", rtnListSub2.get(j).get("CRE_DAT"));
											
					jarySub2.add(jObjSub2);
				}
										
				jObjSub.put("ANS_FREE", jarySub2);
								
				jarySub.add(jObjSub);
			}
								
			jobj.put("RSLT_CD", "00");
			jobj.put("FREE_BUB", jarySub);

	    } catch(Exception e) {
			e.printStackTrace();
			jobj.put("RSLT_CD", "99");
	    } finally {
	    	// 사용다한 세션 닫아주기
	    	if (session != null) session.close();
	    }
	}
    
	public JSONObject getResult() {
		return jobj;
	}

}