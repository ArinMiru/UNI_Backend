/*
 * 대학인증 회원수정 부분 수정
 * userId : 회원 아이디
 * userSccd : 학교코드
 * userDepcd : 학과코드
 * userNum : 학번
 * userEm : 이메일
 */
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
import account.process.MembUniCertUpd;

/**
 * Servlet implementation class LoginSrv
 */
// 占쏙옙占쏙옙占쏙옙抉占� 占쏙옙占쏙옙占쏙옙占� 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙캣 web.xml 占쏙옙 Servlet 占싱띰옙占� 占쏙옙占쏙옙占쌔억옙 占쌩는듸옙 占쏙옙占쏙옙占쏙옙 占실면서 占싣뤄옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙
// URL http://43.201.59.250:8080/ATTENDANCE/Login 占싱띰옙占� 호占쏙옙占싹몌옙 占쏙옙 클占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占�
@WebServlet("/Login")
public class MembUniCertUpdSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembUniCertUpdSrv() {
        super();
        // TODO Auto-generated constructor stub    
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // WEB 호占쏙옙占� GET 占쏙옙占� (占쏙옙占쏙옙占싹곤옙占쏙옙 占싹댐옙 占쌓몌옙占쏙옙 占쏙옙占쏙옙占� 占쌍댐옙 占쏙옙占�)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	// WEB 호占쏙옙占� POST 占쏙옙占� (占쏙옙占쏙옙占싹곤옙占쏙옙 占싹댐옙 占쌓몌옙占쏙옙 占쌕듸옙 占쌓몌옙占쏙옙占쏙옙 占쌍댐옙 占쏙옙占�)
	// 占쎌리占쏙옙 POST 占쏙옙占쏙옙占쏙옙占� JSON 占쏙옙占쌘울옙占쏙옙 占쏙옙占쏙옙占싹곤옙 占쏙옙占쏙옙
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		// 占쏙옙占쏙옙占쏙옙占쏙옙
		String userId=""; 		// 占쏙옙占쏙옙占폠D
		String userSccd="";		// 占싻삼옙占쏙옙占쏙옙
		String userDepcd="";	// 占쏙옙橘占싫�
		String userNum=""; 
		String userEm=""; 
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		
		System.out.println("start");
		
		// JSON 占쏙옙堉깍옙占�
		JSONObject resltObj = new JSONObject();
		
		System.out.println("start2");
		
		// UTF8 占쏙옙占쏙옙
		request.setCharacterEncoding("utf8");
		response.setContentType("application/x-json; charset=UTF-8");
		

		
		// 占쌉력듸옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙 占싻억옙占쏙옙占�
		BufferedReader reader = request.getReader();
		while (( line = reader.readLine()) != null )
		{
			// 占쏙옙占쌘울옙占쏙옙 占쏙옙占� 占쌩곤옙
			jb.append(line);
		}
		// 占쏙옙占쌘울옙占쏙옙 JSON 占쏙옙占쏙옙 占쏙옙환
		JSONObject jobj = JSONObject.fromObject(jb.toString());
		
		// JSON 占쏙옙占쏙옙 占쏙옙占쌨듸옙 占쌓몌옙占쏙옙 占쏙옙占쏙옙
        // for(int i=0;i<jsonArr.size();i++){
		userId = (jobj.get("id") == null) ? "" : jobj.get("id").toString();
		userSccd = (jobj.get("Sccd") == null) ? "" : jobj.get("Sccd").toString();
		userDepcd = (jobj.get("Depcd") == null) ? "" : jobj.get("Depcd").toString();
		userNum = (jobj.get("Num") == null) ? "" : jobj.get("Num").toString();
		userEm = (jobj.get("Em") == null) ? "" : jobj.get("Em").toString();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", userId);
		param.put("userSccd", userSccd);
		param.put("userDepcd", userDepcd);
		param.put("userNum", userNum);
		param.put("userEm", userEm);
		
		// 화占썽에 占쏙옙占쏙옙占쌍깍옙
		System.out.println("userId :".concat(userId));
		System.out.println("userSccd :".concat(userSccd));
		System.out.println("userDepcd :".concat(userDepcd));
		System.out.println("userNum :".concat(userNum));
		System.out.println("userEm :".concat(userEm));
		
		// 占쌓몌옙占쏙옙 占쏙옙占쌘뤄옙 占쌔쇽옙 占쏙옙처占쏙옙 클占쏙옙占쏙옙 호占쏙옙
		//LonginTtableOut longinTtableOut = new LonginTtableOut(userId,userType,userPasswrd);
		account.process.LonginTtableOut longinTtableOut = new account.process.LonginTtableOut(param);
		
		// 호占쏙옙 처占쏙옙占쏙옙占� JSON 占쏙옙 호占쏙옙占쏙옙 클占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
		resltObj = longinTtableOut.getResult();
		
		//resltObj = jobj;
		
		System.out.println("resltObj :".concat(resltObj.toString()));
		
		// 占쏙옙占쏙옙占쏙옙占쏙옙 클占쏙옙占싱억옙트占쏙옙 占싼몌옙占쏙옙
		response.getWriter().print(resltObj);
		
		
	}

}