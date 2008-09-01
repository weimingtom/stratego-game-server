package dnl.games.stratego.client;

import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Random;

import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;

import dnl.games.stragego.ui.BoardUI;

public class StrategoClient implements SimpleClientListener {

    /** The version of the serialized form of this class. */
    private static final long serialVersionUID = 1L;

    /** The name of the host property. */
    public static final String HOST_PROPERTY = "stratego.host";

    /** The default hostname. */
    public static final String DEFAULT_HOST = "localhost";

    /** The name of the port property. */
    public static final String PORT_PROPERTY = "stratego.port";

    /** The default port. */
    public static final String DEFAULT_PORT = "1139";

    /** The random number generator for login names. */
    private final Random random = new Random();
    
	protected final SimpleClient simpleClient = new SimpleClient(this);
	
	private BoardUI boardUI = new BoardUI();
	
    protected void login() {
        String host = System.getProperty(HOST_PROPERTY, DEFAULT_HOST);
        String port = System.getProperty(PORT_PROPERTY, DEFAULT_PORT);

        try {
            Properties connectProps = new Properties();
            connectProps.put("host", host);
            connectProps.put("port", port);
            simpleClient.login(connectProps);
        } catch (Exception e) {
            e.printStackTrace();
            disconnected(false, e.getMessage());
        }
        
        //boardUI,
    }
    
    /**
     * Displays the given string in this client's status bar.
     *
     * @param status the status message to set
     */
    protected void setStatus(String status) {
//        appendOutput("Status Set: " + status);
//        statusLabel.setText("Status: " + status);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Disables input and updates the status message on disconnect.
     */
    public void disconnected(boolean graceful, String reason) {
//        inputPanel.setEnabled(false);
//        setStatus("Disconnected: " + reason);
    }

	@Override
    /**
     * {@inheritDoc}
     * <p>
     * Returns dummy credentials where user is "guest-&lt;random&gt;"
     * and the password is "guest."  Real-world clients are likely
     * to pop up a login dialog to get these fields from the player.
     */
    public PasswordAuthentication getPasswordAuthentication() {
        String player = "guest-" + random.nextInt(1000);
        setStatus("Logging in as " + player);
        String password = "guest";
        return new PasswordAuthentication(player, password.toCharArray());
    }

	@Override
	public void loggedIn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginFailed(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientChannelListener joinedChannel(ClientChannel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receivedMessage(ByteBuffer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnecting() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		StrategoClient strategoClient = new StrategoClient();
		strategoClient.login();
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
