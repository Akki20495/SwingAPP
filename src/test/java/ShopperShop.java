import Pages.OM1;
import org.eclipse.jubula.autagent.Embedded;
import org.eclipse.jubula.client.AUT;
import org.eclipse.jubula.client.AUTAgent;
import org.eclipse.jubula.client.exceptions.CheckFailedException;
import org.eclipse.jubula.client.launch.AUTConfiguration;
import org.eclipse.jubula.toolkit.base.components.GraphicsComponent;
import org.eclipse.jubula.toolkit.concrete.components.ButtonComponent;
import org.eclipse.jubula.toolkit.concrete.components.TextInputComponent;
import org.eclipse.jubula.toolkit.enums.ValueSets;
import org.eclipse.jubula.toolkit.swing.SwingComponents;
import org.eclipse.jubula.toolkit.swing.config.SwingAUTConfiguration;
import org.eclipse.jubula.tools.AUTIdentifier;
import org.eclipse.jubula.tools.ComponentIdentifier;
import org.junit.*;

public class ShopperShop {
    private static TextInputComponent UserName;

    private static TextInputComponent Password;
    private static TextInputComponent FirstName;

    private static TextInputComponent LastName;
    private static TextInputComponent UserName1;

    private static TextInputComponent Password1;
    private static TextInputComponent Zip_Code;

    private static TextInputComponent Date_Of_Birth;

    private static GraphicsComponent Login;

    private static GraphicsComponent Okay;
    private static GraphicsComponent SignUP;

    private static GraphicsComponent Register;

    private static GraphicsComponent WelcomeAboard;

    private AUT m_aut;
    public static final int AUT_AGENT_PORT = 60000;

    /** global prepare */
    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void loadObjectMapping() throws Exception {
        ComponentIdentifier<TextInputComponent> User_Name = OM1.UserName;
        ComponentIdentifier<TextInputComponent> Pass_word = OM1.Password;
        ComponentIdentifier<TextInputComponent> First_Name = OM1.First_Name;
        ComponentIdentifier<TextInputComponent> Last_Name = OM1.Last_Name;
        ComponentIdentifier<TextInputComponent> User_1 = OM1.UserName1;
        ComponentIdentifier<TextInputComponent> Pass_1 = OM1.Password1;
        ComponentIdentifier<TextInputComponent> DOB_ = OM1.DOB;
        ComponentIdentifier<TextInputComponent> Zip_code = OM1.ZipCode;
        ComponentIdentifier<ButtonComponent> Welcome_Aboard = OM1.WelcomeAboard;
        ComponentIdentifier<ButtonComponent> Register_ = OM1.Register;
        ComponentIdentifier<ButtonComponent> Sign_Up = OM1.SignUP_Here;
        ComponentIdentifier<ButtonComponent> Login_ = OM1.Login;
        ComponentIdentifier<ButtonComponent> Okay_ = OM1.Username_NotFound_Okay;

        UserName = SwingComponents.createJTextComponent(User_Name);
        Password = SwingComponents.createJTextComponent(Pass_word);
        FirstName = SwingComponents.createJTextComponent(First_Name);
        LastName = SwingComponents.createJTextComponent(Last_Name);
        UserName1 = SwingComponents.createJTextComponent(User_1);
        Password1 = SwingComponents.createJTextComponent(Pass_1);
        Date_Of_Birth = SwingComponents.createJTextComponent(DOB_);
        Zip_Code = SwingComponents.createJTextComponent(Zip_code);
        WelcomeAboard = SwingComponents.createAbstractButton(Welcome_Aboard);
        Register = SwingComponents.createAbstractButton(Register_);
        SignUP = SwingComponents.createAbstractButton(Sign_Up);
        Login = SwingComponents.createAbstractButton(Login_);
        Okay = SwingComponents.createAbstractButton(Okay_);
    }

    /** prepare */
    @Before
    public void setUp() throws Exception {

        AUTAgent agent = Embedded.INSTANCE.agent(AUT_AGENT_PORT);
        agent.connect();

        final String autID = "shop-sample_swing";

        AUTConfiguration config = new SwingAUTConfiguration(
                "api.aut.conf.simple.adder.swing",
                autID,
                "shop-sample.cmd", //$NON-NLS-1$
                ".\\SimpleAdder\\swing",
                null);


        AUTIdentifier id = agent.startAUT(config);

        if (id != null) {
            m_aut = agent.getAUT(id, SwingComponents.getToolkitInformation());
            m_aut.connect();
        } else {
            Assert.fail("AUT start has failed!"); //$NON-NLS-1$
        }
    }

@Test(expected = CheckFailedException.class)
    public void Enter_Value() throws Exception{
    m_aut.execute( UserName.replaceText("Akhil"),"");
    m_aut.execute( Password.replaceText("Akki"),"");
    m_aut.execute( Login.click(1, ValueSets.InteractionMode.primary),"");
    m_aut.execute( Okay.click(1,ValueSets.InteractionMode.primary),"");
    m_aut.execute( SignUP.click(1, ValueSets.InteractionMode.primary),"");
    m_aut.execute( FirstName.replaceText("Akhil"),"");
    m_aut.execute( LastName.replaceText("Karri"),"");
    m_aut.execute( UserName1.replaceText("Akhil"),"");
    m_aut.execute( Password1.replaceText("Kairos"),"");
    m_aut.execute( Zip_Code.replaceText("500085"),"");
    m_aut.execute( Date_Of_Birth.replaceText("02-04-1995"),"");
    m_aut.execute( Register.click(1,ValueSets.InteractionMode.primary),"");
    m_aut.execute( WelcomeAboard.click(1,ValueSets.InteractionMode.primary),"");

}


    @After
    public void tearDown() throws Exception {
        AUTAgent agent = Embedded.INSTANCE.agent();
        if (m_aut != null) {
            m_aut.disconnect();
            agent.stopAUT(m_aut.getIdentifier());
        }
        agent.disconnect();
    }
}
