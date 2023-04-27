package account.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import account.process.MembReglns;

/**
 * Servlet implementation class LoginSrv
 */
// ������̼� ������� �������� ��Ĺ web.xml �� Servlet �̶�� �����ؾ� �ߴµ� ������ �Ǹ鼭 �Ʒ��� ���� ������
// URL http://43.201.59.250:8080/ATTENDANCE/Login �̶�� ȣ���ϸ� �� Ŭ������ �����
@WebServlet("/MembRegSvc")
public class MembRegSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembRegSrv() {
        super();
        // TODO Auto-generated constructor stub    
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // WEB ȣ��� GET ��� (�����ϰ��� �ϴ� �׸��� ����� �ִ� ���)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	// WEB ȣ��� POST ��� (�����ϰ��� �ϴ� �׸��� �ٵ� �׸����� �ִ� ���)
	// �츮�� POST ������� JSON ���ڿ��� �����ϰ� ����
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		// ��������
		String userId=""; 		// �����ID
		String userName="";		// �л�����
		String userPasswrd="";	// ��й�ȣ
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		
		System.out.println("start");
		
		// JSON ��뼱��
		JSONObject resltObj = new JSONObject();
		
		System.out.println("start2");
		
		// UTF8 ����
		request.setCharacterEncoding("utf8");
		response.setContentType("application/x-json; charset=UTF-8");
		

		
		// �Էµ� ���� ������ �� �о����
		BufferedReader reader = request.getReader();
		while (( line = reader.readLine()) != null )
		{
			// ���ڿ��� ��� �߰�
			jb.append(line);
		}
		// ���ڿ��� JSON ���� ��ȯ
		JSONObject jobj = JSONObject.fromObject(jb.toString());
		
		// JSON ���� ���޵� �׸��� ����
        // for(int i=0;i<jsonArr.size();i++){
		userId = (jobj.get("id") == null) ? "" : jobj.get("id").toString();
		userName = (jobj.get("userName") == null) ? "" : jobj.get("userName").toString();
		userPasswrd = (jobj.get("pass") == null) ? "" : jobj.get("pass").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", userId);
		param.put("userPasswrd", userPasswrd);
		param.put("userType", userName);
		
		// ȭ�鿡 �����ֱ�
		System.out.println("userId :".concat(userId));
		System.out.println("userPasswrd :".concat(userPasswrd));
		System.out.println("userName :".concat(userName));
		
		// �׸��� ���ڷ� �ؼ� ��ó�� Ŭ���� ȣ��
		//LonginTtableOut longinTtableOut = new LonginTtableOut(userId,userType,userPasswrd);
		account.process.MembReglns membreglns = new account.process.MembReglns(param);
		
		// ȣ�� ó����� JSON �� ȣ���� Ŭ������ ���� ��������
		resltObj = membreglns.getResult();
		
		//resltObj = jobj;
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		// �������� Ŭ���̾�Ʈ�� �Ѹ���
		response.getWriter().print(resltObj);
		
		
	}

}
