package chatbot;

public interface IBot {
	public Reply ProcessRequest(String userRequest, int id);
}
