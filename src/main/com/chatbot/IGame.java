package chatbot;

public interface IGame {
	
	String GetIntroductionMessage();
	boolean isActive();
	void SetActive();
	
	void SetInactive();
	boolean isPlayerTurn();
}
