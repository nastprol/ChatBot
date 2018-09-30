package chatbot;

public interface IGame {
	
	Boolean isActive();
	void SetActive();
	void SetInactive();
	
	String GetIntroductionMessage();
		
	String Play(String request);
}
