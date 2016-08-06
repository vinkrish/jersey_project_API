package authentication;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TokenGenerator {
	private SecureRandom random = new SecureRandom();

  public String getToken() {
    return new BigInteger(130, random).toString(32);
  }
}
