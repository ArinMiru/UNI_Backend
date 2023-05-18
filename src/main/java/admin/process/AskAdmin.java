/*
 * 2023.05.17 안재경 <생성>
 * 
 * 게시판 모니터링 (질문 계시판)
 * 
 */

package admin.process;

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

public class AskAdmin {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobjMain = new JSONObject();
	
	public AskAdmin (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Map<String, Object>> rtnList = null;

			rtnList = session.selectList("uni-account-mapping.selectBubInfo",param);
			
			for (int i = 0;i < rtnList.size();i++) {
				jobjMain.put("NICK_NM", rtnList.get(i).get("NICK_NM"));
				jobjMain.put("MEMB_SC_CD", rtnList.get(i).get("MEMB_SC_CD"));
				jobjMain.put("MEMB_DEP_CD", rtnList.get(i).get("MEMB_DEP_CD"));
				jobjMain.put("TIT", rtnList.get(i).get("TIT"));
				jobjMain.put("CRE_DAT", rtnList.get(i).get("CRE_DAT"));
				jobjMain.put("CONT", rtnList.get(i).get("CONT"));
				
				jary.add(rtnList);
				}
			
			
	
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jobjMain;
	}

}