package chatbot;

public interface IGame {
	
	boolean isActive();
	void SetActive();
	void SetInactive();
	
	String GetIntroductionMessage();
}
