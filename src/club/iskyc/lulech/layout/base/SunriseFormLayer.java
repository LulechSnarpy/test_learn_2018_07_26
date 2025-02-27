package club.iskyc.lulech.layout.base;

import club.iskyc.lulech.Interaction.SunriseEventCenter;
import club.iskyc.lulech.Interaction.SunriseEventEnum;
import club.iskyc.lulech.layout.bean.SunriseMethodInfo;
import club.iskyc.lulech.layout.util.MarkedElement;
import club.iskyc.lulech.layout.util.SunriseMethodUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is constants for input params for method.
 *
 * @author lulec
 * @since 1.0
 * */
public class SunriseFormLayer extends JPanel {
    /**
     * For components transform actions.
     * */
    private final SunriseEventCenter eventCenter;

    /**
     * For remind for inputs.
     * */
    private List<JTextField> inputs = new ArrayList<>();

    /**
     * Remind last method info.
     */
    private SunriseMethodInfo methodInfo;

    /**
     * Create new  {@code SunriseFormPanel}.
     * */
    public SunriseFormLayer() {
        eventCenter = SunriseEventCenter.getInstance();
        eventCenter.addObserver("SunriseFormPanel.initForm", o ->  {
            if (null == o) return;
            SunriseMethodInfo methodInfo = (SunriseMethodInfo) o;
            initForm(methodInfo);
        });
    }

    /**
     * Initialize panel's form by method information.
     * */
    public void initForm(SunriseMethodInfo methodInfo) {
        SunriseMethodUtil.prepareForm(methodInfo, this);
        JLabel jLabel = new JLabel(methodInfo.method().getName().toUpperCase(), JLabel.TRAILING);
        JButton jButton = new JButton("run");
        jButton.addActionListener(e -> {
            eventCenter.addEvent(
                    new MarkedElement<>(SunriseEventEnum.INVOKE_METHOD.getMark(), methodInfo));
        });
        this.add(jLabel);
        jLabel.setLabelFor(jButton);
        this.add(jButton);
        this.updateUI();
    }

    /**
     * Return form inputs in this component.
     * */
    public List<JTextField> inputs(){ return inputs;}
}
