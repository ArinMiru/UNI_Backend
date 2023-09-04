/*
 * DprtSrch : 학과 검색
 * 2023.09.03 김도원 생성	 
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

public class DprtSrch {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public DprtSrch (Map<String, Object> param) throws IOException {
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
			JSONArray jarySub2 = new JSONArray();
			JSONObject jObjSub2 = new JSONObject();
			List<Map<String, Object>> rtn = null;
		    rtn = session.selectList("uni-account-mapping.selectSchlAndDprtSrch", param);
		    
		    if (rtn == null) {
		        jObjMain.put("RSLT_CD", "01");
		    } else {
		    	for (int j=0; j < rtn.size() ;j++)
				{
					jObjSub2.put("SCH_CD", rtn.get(j).get("SCH_CD"));
		    		jObjSub2.put("SCH_NM", rtn.get(j).get("SCH_NM"));
		    		jObjSub2.put("DPRT_CD", rtn.get(j).get("DPRT_CD"));
		    		jObjSub2.put("DPRT_NM", rtn.get(j).get("DPRT_NM"));
		    		
		    		jarySub2.add(jObjSub2);
		        }
		    	jObjMain.put("RSLT_CD", "00");			
		    	jObjMain.put("DPRT_NM_INFO", jarySub2);   
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