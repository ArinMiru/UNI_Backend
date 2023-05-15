package mail.process;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthentication extends Authenticator {
	PasswordAuthentication pa;
	public MyAuthentication(){
		String id="myp.uni.ptu@gmail.com"; // 구글계정
		String pw="pupojtrvdpogtpbr"; // 구글 앱번호
		
		pa = new PasswordAuthentication(id, pw);
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}