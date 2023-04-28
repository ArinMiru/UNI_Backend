package community.suggestion.process;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LonginTtableOut {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobj = new JSONObject();
	
	public LonginTtableOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL ������ ���� �⺻ ���� ����
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			//pstmt.setString(1, userPasswrd);
			//pstmt.setString(2, userId);
			//pstmt.setString(3, userType);
			
			Map<String, Object> rtn = new HashMap<String, Object>();
			
			rtn = session.selectOne("uni-mapping.selectInfo",param);
			
			System.out.println(rtn.get("IND"));
			
			// rsltCd �׸� �߰�
			// �÷������� JSON �׸� �� ���� ����
			//jobj.put("FLAG", rtn.get("IND"));
			// userId �׸� �߰�
			jobj.put("userId", param.get("userId"));
			// userType �׸� �߰�
			jobj.put("userType", param.get("userType"));
			// userPasswrd �׸� �߰� ( ������ ���� �������� ���� )
			jobj.put("userPasswrd", param.get("userPasswrd"));

			// ������ �����Ѱ� �ݾ��ֽ�
			
            JSONObject jo=new JSONObject();
			jo.put("FLAG", rtn.get("IND"));
			jo.put("FLAG1", rtn.get("NUM"));
			jary.add(jo);
		    
			// �ð�ǥ�� TT_LIST ��� ��ǥ������ �迭 ���·� ����
			jobj.put("TT_LIST", jary);

	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		// ������� ����
		return jobj;
	}

}