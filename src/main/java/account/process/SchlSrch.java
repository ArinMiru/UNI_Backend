/*
 * SchlSrchSrv : 대학교 검색
 * (2023.09.03 김도원 생성)
 */

package account.process;

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

public class SchlSrch {    
	
	private JSONObject jObjMain = new JSONObject();
	
	public SchlSrch (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			JSONArray jarySub2 = new JSONArray();
			JSONObject jObjSub2 = new JSONObject();
			List<Map<String, Object>> rtn = null;
		    rtn = session.selectList("uni-account-mapping.selectSchlSrch", param);
		    
		    if (rtn == null) {
		        jObjMain.put("RSLT_CD", "01");
		    } else {
		    	for (int j=0; j < rtn.size() ;j++)
				{
					jObjSub2.put("SCH_CD", rtn.get(j).get("SCH_CD"));
					jObjSub2.put("SCH_NM", rtn.get(j).get("SCH_NM"));
								
					jarySub2.add(jObjSub2);
				}
		    	jObjMain.put("RSLT_CD", "00");			
		    	jObjMain.put("SCH_NM_INFO", jarySub2);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if (session != null) {
		        session.close();
		    }
		}
	}
	public JSONObject getResult() {
		return jObjMain;
	}
}
