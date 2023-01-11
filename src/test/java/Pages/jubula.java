package Pages;

import org.eclipse.jubula.autagent.Embedded;
import org.eclipse.jubula.client.AUT;
import org.eclipse.jubula.client.AUTAgent;
import org.eclipse.jubula.client.Result;
import org.eclipse.jubula.client.launch.AUTConfiguration;
import org.eclipse.jubula.toolkit.base.components.GraphicsComponent;
import org.eclipse.jubula.toolkit.concrete.components.ButtonComponent;
import org.eclipse.jubula.toolkit.concrete.components.TextComponent;
import org.eclipse.jubula.toolkit.concrete.components.TextInputComponent;
import org.eclipse.jubula.toolkit.enums.ValueSets;
import org.eclipse.jubula.toolkit.swing.SwingComponents;
import org.eclipse.jubula.toolkit.swing.config.SwingAUTConfiguration;
import org.eclipse.jubula.tools.AUTIdentifier;
import org.eclipse.jubula.tools.ComponentIdentifier;
import org.junit.Assert;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class jubula {
    private static TextInputComponent value1;
    /** the value2 */
    private static TextInputComponent value2;
    /** the button */
    private static GraphicsComponent button;
    /** the result */
    private static TextComponent result;

    /** the AUT */
    private AUT m_aut;
    public static final int AUT_AGENT_PORT = 60000;
    public void exewindow(){
        ComponentIdentifier<TextInputComponent> val1Id = OM.value1;
        ComponentIdentifier<TextInputComponent> val2Id = OM.value2;
        ComponentIdentifier<ButtonComponent> buttonId = OM.equalsButton;
        ComponentIdentifier<TextComponent> sumId = OM.resultField;

        value1 = SwingComponents.createJTextComponent(val1Id);
        value2 = SwingComponents.createJTextComponent(val2Id);
        button = SwingComponents.createAbstractButton(buttonId);
        result = SwingComponents.createJLabel(sumId);
        AUTAgent agent = Embedded.INSTANCE.agent(AUT_AGENT_PORT);
        agent.connect();

        final String autID = "SimpleAdder_swing";

        AUTConfiguration config = new SwingAUTConfiguration(
                "api.aut.conf.simple.adder.swing",
                autID,
                "launch.cmd", //$NON-NLS-1$
                new StringBuilder()
                        .append(FileSystems.getDefault().getPath(".").toAbsolutePath().normalize().toString())
                        .append("\\SimpleAdder\\swing")
                        .toString(),
                null);


        AUTIdentifier id = agent.startAUT(config);

        if (id != null) {
            m_aut = agent.getAUT(id, SwingComponents.getToolkitInformation());
            m_aut.connect();
        } else {
            Assert.fail("AUT start has failed!"); //$NON-NLS-1$
        }
        final int firstValue = 17;
        List<Result<String>> results = new ArrayList<Result<String>>();
        try {
            for (int i = 1; i < 5; i++) {
                results.add(m_aut.execute(
                        value1.replaceText(String.valueOf(firstValue)),
                        "Entering first value")); //$NON-NLS-1$
                results.add(m_aut.execute(
                        value2.replaceText(String.valueOf(i)),
                        "Entering second value")); //$NON-NLS-1$
                results.add(m_aut.execute(
                        button.click(1, ValueSets.InteractionMode.primary),
                        "Invoking calculation")); //$NON-NLS-1$
                results.add(m_aut.execute(result.checkText(
                                String.valueOf(firstValue + i), ValueSets.Operator.equals),
                        "Checking result")); //$NON-NLS-1$
            }
        } finally {
            Assert.assertTrue(results.size() == 15);
        }
        agent = Embedded.INSTANCE.agent();
        if (m_aut != null) {
            m_aut.disconnect();
            agent.stopAUT(m_aut.getIdentifier());
        }
        agent.disconnect();
    }




    }




