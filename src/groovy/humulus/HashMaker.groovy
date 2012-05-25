package humulus;

import org.mindrot.jbcrypt.BCrypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Create hash digests of a string, salted with a salt value from the config.  
 *
 */
public class HashMaker {

    static Integer passwordRounds = 10; // default of jBcrypt
    
	/**
	 * Use Bcrypt to create a hashed string from a given text.
	 * Use before storing a password in the database.<br>
	 * Used by server.UserAccount.
     * @param text the password string from which a digest will be created.
     * @return a digest based upon the configured number of hash generation rounds and the specified plain-text.
	 */
	public static String createDigest(String text){
		Logger log = LoggerFactory.getLogger(HashMaker.class);
		Integer rounds = passwordRounds
		String digest = BCrypt.hashpw(text, BCrypt.gensalt(rounds));
		log.debug("digest:"+digest);
		return digest;
	}

    /**
     * Compare the hashed version of a given text with a specified hash string.
     * This method is used by the class SecurityController in Dandelion.
     * @param text the plain text password
     * @param hash the password hash
     * @return true if the hash is neither null nor different from the hashed text.
     */
	public static Boolean compareWithHash(String text, String hash){
        try{
            return (hash != null) && ( BCrypt.checkpw(text, hash) );
        }
        catch (Exception e){
            Logger log = LoggerFactory.getLogger(HashMaker.class);
            log.warn("An exception occurred - failed to check password. Access denied!",e);
            return false;
        }
	}
}
