package passwordgenerator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderGenerator {

	public static void main(String[] args) {
		String input = "1234";
		String output = "";
		BCryptPasswordEncoder generator = new BCryptPasswordEncoder();
		output = generator.encode(input);
		System.out.println("password: " + input);
		System.out.println("hashed: " + output);
	}

}
