package SerenityWithJubula;

import org.eclipse.jubula.autagent.Embedded;
import org.eclipse.jubula.client.AUT;
import org.eclipse.jubula.client.AUTAgent;
import org.eclipse.jubula.client.exceptions.CheckFailedException;
import org.eclipse.jubula.client.launch.AUTConfiguration;
import org.eclipse.jubula.toolkit.base.components.GraphicsComponent;
import org.eclipse.jubula.toolkit.concrete.components.ButtonComponent;
import org.eclipse.jubula.toolkit.concrete.components.ComboComponent;
import org.eclipse.jubula.toolkit.concrete.components.SliderComponent;
import org.eclipse.jubula.toolkit.concrete.components.TableComponent;
import org.eclipse.jubula.toolkit.enums.ValueSets;
import org.eclipse.jubula.toolkit.swing.SwingComponents;
import org.eclipse.jubula.toolkit.swing.config.SwingAUTConfiguration;
import org.eclipse.jubula.tools.AUTIdentifier;
import org.eclipse.jubula.tools.ComponentIdentifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.FileSystems;

import static org.eclipse.jubula.toolkit.enums.ValueSets.Operator.equals;
import static org.eclipse.jubula.toolkit.enums.ValueSets.SearchType.absolute;

public class Swingset {
    private static GraphicsComponent GotoSlider;

    private static GraphicsComponent DropDown;
    private static GraphicsComponent TableButton;



    private static GraphicsComponent Close;

    private static GraphicsComponent Maximum;
    private static GraphicsComponent Minimum;

    private static ComboComponent DropDownName;
    private static SliderComponent Slider;
    private static TableComponent Select_Table;
    private AUT m_aut;
    public static final int AUT_AGENT_PORT = 60000;
    @BeforeClass
    public static void loadObjectMapping() throws Exception {
        ComponentIdentifier<ButtonComponent> GotoSlider_ = SwingSet2.GotoSlider;
        ComponentIdentifier<ButtonComponent> DropDown_ = SwingSet2.DropDown;
        ComponentIdentifier<ButtonComponent> TableButton_ = SwingSet2.TableButton;
        ComponentIdentifier<ButtonComponent> Close_ = SwingSet2.Close;
        ComponentIdentifier<ButtonComponent> Maximum_ = SwingSet2.Maximum;
        ComponentIdentifier<ButtonComponent> Minimum_ = SwingSet2.Minimum;
        ComponentIdentifier<ComboComponent> DropDownName_ = SwingSet2.DropDownName;
        ComponentIdentifier<SliderComponent> Slider_ = SwingSet2.Slider;
        ComponentIdentifier<TableComponent> Select_Table_ = SwingSet2.Select_Table;
        ComponentIdentifier<TableComponent> changeText = SwingSet2.changeText;

        GotoSlider = SwingComponents.createAbstractButton(GotoSlider_);
        DropDown = SwingComponents.createAbstractButton(DropDown_);
        TableButton = SwingComponents.createAbstractButton(TableButton_);
        Close = SwingComponents.createAbstractButton(Close_);
        Maximum = SwingComponents.createAbstractButton(Maximum_);
        Minimum = SwingComponents.createAbstractButton(Minimum_);
        DropDownName = SwingComponents.createJComboBox(DropDownName_);
        Slider = SwingComponents.createJSlider(Slider_);
        Select_Table =SwingComponents.createJTable(Select_Table_);


    }

    /** prepare */
    @Before
    public void setUp() throws Exception {
        AUTAgent agent = Embedded.INSTANCE.agent(AUT_AGENT_PORT);
        agent.connect();

        final String autID = "SimpleAdder_swing";

        AUTConfiguration config = new SwingAUTConfiguration(
                "api.aut.conf.simple.adder.swing",
                autID,
                "combo.cmd", //$NON-NLS-1$
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
    }
    @Test(expected = CheckFailedException.class)
    public void Enter_Value() throws Exception {
//        Maximum, Minimum and close
        m_aut.execute(Maximum.click(1, ValueSets.InteractionMode.primary), "");
        m_aut.execute(Minimum.click(1, ValueSets.InteractionMode.primary), "");
        m_aut.execute(Close.click(1, ValueSets.InteractionMode.primary), "");

//        Slider
        m_aut.execute(GotoSlider.click(1, ValueSets.InteractionMode.primary), "Invoking calculation");
        m_aut.execute(Slider.selectPosition("82", equals , ValueSets.Measure.value),"");

//       DropDown
        m_aut.execute(DropDown.click(1, ValueSets.InteractionMode.primary), "");
        m_aut.execute(DropDownName.selectEntryByValue("Lisa", equals, absolute),"");
        
//       TableSelection
        m_aut.execute(TableButton.click(1, ValueSets.InteractionMode.primary), "");
        m_aut.execute(Select_Table.selectCell("3", equals,"2", equals,2,50,ValueSets.Unit.percent,50,ValueSets.Unit.percent, ValueSets.BinaryChoice.no,ValueSets.InteractionMode.primary),"");

        m_aut.execute( Select_Table.replaceText("Akhil"),"");
    }
}
