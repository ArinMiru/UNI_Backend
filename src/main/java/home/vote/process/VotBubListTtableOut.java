package home.vote.process;

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

public class VotBubListTtableOut {    
	
	private JSONObject jobj = new JSONObject();
	
	public VotBubListTtableOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// mybatis-config.xml 을 이용하여 db 커넥션 생성
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			
			// 투표은 LIST 형태로 조회되기 때문에 LIST 선언
			List<Map<String, Object>> rtnList = null;			
								
			rtnList = session.selectList("uni-home-mapping.selectVotBubInfo",param);	
									
			JSONArray jarySub = new JSONArray();
			JSONObject jObjSub = new JSONObject();
									
									
			// 투표은 다건일수 있기에 selectList 로 호출하며 loop 를 수행하여 레코드별 JSON 형태로 만들어준다
			for (int i=0; i < rtnList.size() ;i++)
			{
				jObjSub.put("MEMB_ID", rtnList.get(i).get("MEMB_ID"));
				jObjSub.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
				jObjSub.put("VOT_TITLE", rtnList.get(i).get("VOT_TITLE"));
				jObjSub.put("VOT_TYPE_CD", rtnList.get(i).get("VOT_TYPE_CD"));
				jObjSub.put("VOT_EXPR_DATE", rtnList.get(i).get("VOT_EXPR_DATE"));
				jObjSub.put("VOT_DESC", rtnList.get(i).get("VOT_DESC"));
				jObjSub.put("VOT_GO_CD", rtnList.get(i).get("VOT_GO_CD"));
				jObjSub.put("VOT_SEL_SEQ", rtnList.get(i).get("VOT_SEL_SEQ"));
				jObjSub.put("VOT_INFO", rtnList.get(i).get("VOT_INFO"));
				
				jObjSub.put("MEMB_SC_NM", rtnList.get(i).get("MEMB_SC_NM"));
				jObjSub.put("MEMB_DEP_NM", rtnList.get(i).get("MEMB_DEP_NM"));
				jObjSub.put("TIT_NM", rtnList.get(i).get("TIT_NM"));
				jObjSub.put("NICK_NM", rtnList.get(i).get("NICK_NM"));
								
				jarySub.add(jObjSub);
			}
								
			jobj.put("RSLT_CD", "00");
			jobj.put("VOT_BUB", jarySub);

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