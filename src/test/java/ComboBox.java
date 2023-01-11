import Pages.OM3;
import org.eclipse.jubula.autagent.Embedded;
import org.eclipse.jubula.client.AUT;
import org.eclipse.jubula.client.AUTAgent;
import org.eclipse.jubula.client.exceptions.CheckFailedException;
import org.eclipse.jubula.client.launch.AUTConfiguration;
import org.eclipse.jubula.toolkit.base.components.GraphicsComponent;
import org.eclipse.jubula.toolkit.concrete.components.ButtonComponent;
import org.eclipse.jubula.toolkit.concrete.components.ComboComponent;
import org.eclipse.jubula.toolkit.enums.ValueSets;
import org.eclipse.jubula.toolkit.swing.SwingComponents;
import org.eclipse.jubula.toolkit.swing.config.SwingAUTConfiguration;
import org.eclipse.jubula.tools.AUTIdentifier;
import org.eclipse.jubula.tools.ComponentIdentifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.eclipse.jubula.toolkit.enums.ValueSets.Operator.equals;
import static org.eclipse.jubula.toolkit.enums.ValueSets.SearchType.absolute;

public class ComboBox {
    private static GraphicsComponent Button;
    private static ComboComponent DropDown;

    private AUT m_aut;
    public static final int AUT_AGENT_PORT = 60000;


    @BeforeClass
    public static void loadObjectMapping() throws Exception {
        ComponentIdentifier<ButtonComponent> Click_ = OM3.click;
        ComponentIdentifier<ComboComponent> Select_ = OM3.select;
        Button = SwingComponents.createAbstractButton(Click_);
        DropDown = SwingComponents.createJComboBox(Select_);
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
        m_aut.execute(Button.click(1, ValueSets.InteractionMode.primary), "");
        m_aut.execute(DropDown.selectEntryByValue("Lisa", equals, absolute),"");

    }


}