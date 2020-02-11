package edu.eci.arsw.springdemo;
import org.springframework.stereotype.Service;

@Service("SpellChecker")
public interface SpellChecker {

	public String checkSpell(String text);
	
}
