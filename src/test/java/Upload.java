import Pages.OM4;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Upload {
    private static GraphicsComponent Upload_;
    private static GraphicsComponent GotoUpload_;
    private static GraphicsComponent SeleKt_;
    private static TextInputComponent Path_;

    private AUT m_aut;
    public static final int AUT_AGENT_PORT = 60000;


        @BeforeClass
        public static void loadObjectMapping() throws Exception {
            ComponentIdentifier<ButtonComponent> Upload = OM4.Up_Load;
            ComponentIdentifier<ButtonComponent> GOTOUPLOAD = OM4.GOTOUPLOAD;
            ComponentIdentifier<TextInputComponent> Path = OM4.Path;
            ComponentIdentifier<ButtonComponent> SeLEKT = OM4.SELEKTUPLOAD;
            ComponentIdentifier<ButtonComponent> GOTOSLIDER_ = OM4.GOTOSlider;



            Upload_ = SwingComponents.createAbstractButton(Upload);
            GotoUpload_ = SwingComponents.createAbstractButton(GOTOUPLOAD);
            SeleKt_ = SwingComponents.createAbstractButton(SeLEKT);
            Path_ = SwingComponents.createJTextComponent(Path);

        }

        @Before
        public void setUp() throws Exception {

            AUTAgent agent = Embedded.INSTANCE.agent(AUT_AGENT_PORT);
            agent.connect();

            final String autID = "shop-sample_swing";

            AUTConfiguration config = new SwingAUTConfiguration(
                    "api.aut.conf.simple.adder.swing",
                    autID,
                    "combo.cmd", //$NON-NLS-1$
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
        public void Enter_Value() throws Exception {
            m_aut.execute(GotoUpload_.click(1, ValueSets.InteractionMode.primary), "");
            m_aut.execute(SeleKt_.click(1, ValueSets.InteractionMode.primary), "");
            m_aut.execute(Path_.replaceText("C:\\Users\\AkhilK-Kairos\\OneDrive - Kairos Technologies Inc\\Desktop"),"");
            m_aut.execute(Upload_.click(1, ValueSets.InteractionMode.primary),"");


        }
        }
