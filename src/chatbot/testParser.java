package chatbot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class testParser {
	
	@Test
	void testCorrectResponseInIncorrectPlayerAnswer()
	{
		BattleSea game = new BattleSea();
		Reply reply = null;
		Parser parser = new  Parser(game);
		int id = 1;
		game.SetActive();
		game.setPlayerTurn();
		reply = parser.ProcessPlayerAnswer("miss", id);
		assertEquals(reply.botAnswer, "It's your turn to shoot");
		reply = parser.ProcessPlayerAnswer("damage", id);
		assertEquals(reply.botAnswer, "It's your turn to shoot");
		reply = parser.ProcessPlayerAnswer("kill", id);
		assertEquals(reply.botAnswer, "It's your turn to shoot");
		reply = parser.ProcessPlayerAnswer("hygtfrd", id);
		assertEquals(reply.botAnswer, "Send me coordinates in format <A-J> <1-10>");
		game.setNotPlayerTurn();
		reply = parser.ProcessPlayerAnswer("a 6", id);
		assertEquals(reply.botAnswer, "It's not your turn to shoot");
	}
	
	@Test
	void testCorrectResponseInCorrectPlayerAnswer()
	{
		BattleSea game = new BattleSea();
		Reply reply = null;
		Parser parser = new  Parser(game);
		int id = 1;
		game.SetActive();
		game.setNotPlayerTurn();
		reply = parser.ProcessPlayerAnswer("miss", id);
		assertEquals(reply.botAnswer, "Your turn to shoot");
		game.setNotPlayerTurn();
		reply = parser.ProcessPlayerAnswer("damage", id);
		String[] anwser = reply.botAnswer.split(" ");
		//System.out.println(anwser[0]);
		assertEquals(Character.isDigit(anwser[1].charAt(0)), true);
		assertEquals(Character.isLetter(anwser[0].charAt(0)), true);
		game.setPlayerTurn();
		reply = parser.ProcessPlayerAnswer("a 4", id);
		String report = reply.botAnswer.substring(0, 4);
		assertEquals(report.equals("miss")  || report.equals("kill")  || report.equals("dama"), true);
		
	}

}
