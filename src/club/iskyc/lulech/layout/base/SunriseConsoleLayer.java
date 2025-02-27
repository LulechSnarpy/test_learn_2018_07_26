package club.iskyc.lulech.layout.base;

import club.iskyc.lulech.Interaction.SunriseEventCenter;
import club.iskyc.lulech.Interaction.SunriseEventEnum;
import club.iskyc.lulech.layout.bean.SunriseMethodInfo;

import javax.swing.*;

/**
 * This is class is a window constant for show console infos.
 * */
public class SunriseConsoleLayer extends JTextArea {
    /**
     * For components transform actions.
     * */
    private final SunriseEventCenter eventCenter;

    /**
     * Remind last method info.
     */
    private SunriseMethodInfo methodInfo;

    /**
     * Create new {@code SunriseConsoleLayer}.
     * */
    public SunriseConsoleLayer() {
        eventCenter = SunriseEventCenter.getInstance();
    }

    /**
     * When method info on choose change the console.
     * */
    public void onMethodChosen(SunriseMethodInfo methodInfo) {
        if (!isSameMethodInfo(methodInfo)) {
            eventCenter.removeObserver(SunriseEventEnum.CONSOLE_SHOW.getMark(this.methodInfo));
            eventCenter.addObserver(SunriseEventEnum.CONSOLE_SHOW.getMark(methodInfo),
                    o -> {
                         if (null == o) return;
                         showConsole((SunriseMethodInfo) o);
                    });
            this.methodInfo = methodInfo;
        }
        showConsole(methodInfo);
    }

    /**
     * Show current method info console string in this layer.
     * */
    public void showConsole(SunriseMethodInfo methodInfo) {
        this.setText(methodInfo.consoleInfoBytes().toString());
        this.invalidate();
    }

    /**
     * Whether last method info equals current one.
     * */
    private boolean isSameMethodInfo(SunriseMethodInfo currentMethodInfo) {
        return null != methodInfo && methodInfo.equals(currentMethodInfo);
    }


}
