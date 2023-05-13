package home.schedule.process;

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

public class SchdBubDtlListTtableOut {    
	
	private JSONObject jobj = new JSONObject();
	
	public SchdBubDtlListTtableOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// mybatis-config.xml 을 이용하여 db 커넥션 생성
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			
			// 스케쥴 상세은 LIST 형태로 조회되기 때문에 LIST 선언
			List<Map<String, Object>> rtnList = null;			
								
			rtnList = session.selectList("uni-home-mapping.selectSchdDtlBubInfo",param);	
									
			JSONArray jarySub = new JSONArray();
			JSONObject jObjSub = new JSONObject();			
									
			// 스케쥴 상세은 다건일수 있기에 selectList 로 호출하며 loop 를 수행하여 레코드별 JSON 형태로 만들어준다
			for (int i=0; i < rtnList.size() ;i++)
			{
				
				jObjSub.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
				jObjSub.put("TIT", rtnList.get(i).get("TIT"));
				jObjSub.put("CONT", rtnList.get(i).get("CONT"));
				jObjSub.put("STRT_SCHD_YMD", rtnList.get(i).get("STRT_SCHD_YMD"));
				jObjSub.put("END_SCHD_YMD", rtnList.get(i).get("END_SCHD_YMD"));
				jObjSub.put("CRE_DAT", rtnList.get(i).get("CRE_DAT"));
				
				jarySub.add(jObjSub);
			}
								
			jobj.put("RSLT_CD", "00");
			jobj.put("SCHD_BUB", jarySub);

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