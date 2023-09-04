/*
 * SchlSrchSrv : 대학교 검색
 * (2023.09.03 김도원 생성)
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class SchlSrch {    
	
	private JSONObject jObjMain = new JSONObject();
	
	public SchlSrch (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
		    Map<String, Object> rtn = null;
		    rtn = session.selectOne("uni-account-mapping.selectSchlSrch", param);
		    
		    if (rtn == null) {
		        jObjMain.put("RSLT_CD", "01");
		    } else {
		        if ("00".equals(rtn.get("RSLT_CD"))) {
		            jObjMain.put("SCH_CD", rtn.get("SCH_CD"));  // Integer로 캐스팅
		            jObjMain.put("SCH_NM", rtn.get("SCH_NM"));
		            jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
		        } else {
		            jObjMain.put("RSLT_CD", "01");
		        }
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
