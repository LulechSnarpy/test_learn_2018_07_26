package club.iskyc.lulech.layout.base;

import club.iskyc.lulech.Interaction.SunriseEventCenter;
import club.iskyc.lulech.Interaction.SunriseEventEnum;
import club.iskyc.lulech.Interaction.SunriseThreadPool;
import club.iskyc.lulech.layout.bean.SunriseMethodInfo;
import club.iskyc.lulech.layout.listener.FunctionListener;
import club.iskyc.lulech.layout.util.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *  This class registered all then <em>Window Constants</em>.
 * <p>
 *  And can be a factory class for those constants.
 * </p>
 *
 * @author lulec
 * @since 1.0
 */
public class SunriseMainLayer implements BaseConstantValue,SunriseLayerConstant {
    // Windows Constants
    /**
     * This is the main frame for the all window constants.
     */
    private JFrame basicLayer;

    // Tool Bar
    /**
     *  This is a toolbar for the main frame.
     * */
    private JMenuBar menuBarLayer;

    // On the left stage
    /**
     * This is panel for tree selector
     * */
    private JScrollPane treePanelLayer;

    // On the center stage
    /**
     * This one contains form for input.
     * */
    private SunriseFormLayer formPanelLayer;


    // On the bottom stage
    /**
     * This layer contains window constants for laying console info.
     * */
    private JScrollPane consoleScrollLayer;

    /**
     * This window constant is for laying console info.
     * */
    private SunriseConsoleLayer consoleLayer;

    // Listeners
    /**
     * This listener is for prepare run choose class's methods.
     * */
    private FunctionListener functionListener;

    // Extra Components
    /**
     * Data for method infos, store each method information.
     * */
    private List<SunriseMethodInfo> methodinfos;

    /**
     * Data for tree method infos, for tree type used.
     * */
    private Tree<MarkedElement<String, SunriseMethodInfo>> treeSunriseMethodInfo;

    /**
     * For components transform actions.
     * */
    private SunriseEventCenter eventCenter;

    /**
     * For thread create and use, thread pool.
     */
    private ThreadPoolExecutor threadPool;

    /**
     *  Creates a new {@code SunriseMainLayer}.
     *  <p>This can be a factory creator for all windows constants.</p>
     * */
    public SunriseMainLayer() {
        initData();
        initMainStage();
        initCenterStage();
        initBottomStage();
        initToolBar();
        initLeftStage();
        combineLayers();
        addObservers();
    }

    /**
     *
     * */
    private void initData() {
        methodinfos = SunriseMethodUtil.getSunriseMethodInfos();
        treeSunriseMethodInfo = SunriseMethodUtil.getTreeSunriseMethodInfo(methodinfos);
        eventCenter = SunriseEventCenter.getInstance();
        threadPool = SunriseThreadPool.getInstance();
    }

    /**
     * Create main frame {@code basicLayer} and init all the params.
     * */
    private void initMainStage() {
        basicLayer = new JFrame("Sunrise");
        basicLayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        basicLayer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        basicLayer.setEnabled(true);
        basicLayer.setPreferredSize(new Dimension(outerWidth, outerHeight));
        basicLayer.setBounds(layingStartX, layingStartY, outerWidth, outerHeight);
        basicLayer.setLayout(new GridLayout(0, 2));
    }

    /**
     * Create main frame {@code menuBarLayer} and init all the params.
     * */
    private void initToolBar() {
        menuBarLayer = // new JMenuBar();
             SMenuBar.getSToolBar(consoleLayer, formPanelLayer);
    }

    /**
     * Create {@code treePanelLayer} and init all the params.
     * */
    private void initLeftStage() {
        treePanelLayer = new JScrollPane();
        treePanelLayer.setBackground(Color.white);
        treePanelLayer.setPreferredSize(new Dimension( 200, outerHeight));
        TreeNode<MarkedElement<String, SunriseMethodInfo>> head
                = treeSunriseMethodInfo.getHeadNode();
        DefaultMutableTreeNode top
                = new DefaultMutableTreeNode(head.getData().getMark());
        createNodes(top, head);
        JTree tree = new JTree(top);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            if(null != node && node.isLeaf()) {
                SunriseMethodInfo methodInfo = (SunriseMethodInfo) node.getUserObject();
                formPanelLayer.initForm(methodInfo);
                consoleLayer.onMethodChosen(methodInfo);
            }
        });
        treePanelLayer.setViewportView(tree);
    }

    /**
     * Create {@code JTree} according to the tree with method infos.
     * */
    private void createNodes(DefaultMutableTreeNode top
            , TreeNode<MarkedElement<String, SunriseMethodInfo>> parent) {
        List<TreeNode<MarkedElement<String, SunriseMethodInfo>>> leaves = parent.getLeaves();
        if (leaves.isEmpty()) {
            top.setUserObject(parent.getData().getData());
            return;
        }
        String mark;
        for (TreeNode<MarkedElement<String, SunriseMethodInfo>> leaf : leaves) {
            int preIndex = leaf.getData().getMark().lastIndexOf(packageSplitter);
            leaf = shrinkPath(leaf);
            mark = leaf.getData().getMark();
            DefaultMutableTreeNode node
                    = new DefaultMutableTreeNode(
                            mark.substring(preIndex + 1));
            top.add(node);
            createNodes(node, leaf);
        }
    }

    /**
     * Shrink path method.
     * */
    private TreeNode<MarkedElement<String, SunriseMethodInfo>>
        shrinkPath(TreeNode<MarkedElement<String, SunriseMethodInfo>> node) {
        if (node.getLeaves().size() != 1) return node;
        while (node.getLeaves().getFirst().getLeaves().size() == 1) {
            node = node.getLeaves().getFirst();
        }
        return node;
    }

    /**
     * Create {@code formPanelLayer} and init all the params.
     * */
    private void initCenterStage() {
        formPanelLayer = new SunriseFormLayer();
        formPanelLayer.setBackground(Color.CYAN);
        formPanelLayer.setPreferredSize(
                new Dimension(outerWidth - 2 * basicBorderWidth -200, formLayerHeight));
    }

    /**
     * Create {@code consoleLayer}({@code SunriseConsoleLayer}) {@code consoleScrollLayer},
     * and init all prams.
     * */
    private void initBottomStage() {
        consoleLayer = new SunriseConsoleLayer();
        consoleLayer.setEnabled(Boolean.FALSE);
        consoleScrollLayer = new JScrollPane(consoleLayer);
        consoleScrollLayer.setPreferredSize(
                new Dimension(outerWidth - 2 * basicBorderWidth -200
                        , outerHeight - formLayerHeight - 2 * basicBorderWidth));

    }

    /**
     * Add window constants into their parents constants.
     * <p>And make main frame visible</p>
     * */
    private void combineLayers() {
        basicLayer.setJMenuBar(menuBarLayer);
        basicLayer.add(treePanelLayer, BorderLayout.WEST);
        JPanel jPanel = new JPanel();
        jPanel.add(formPanelLayer, BorderLayout.CENTER);
        jPanel.add(consoleScrollLayer, BorderLayout.SOUTH);
        // basicLayer.add(formPanelLayer, BorderLayout.CENTER);
        // basicLayer.add(consoleScrollLayer, BorderLayout.SOUTH);
        basicLayer.add(jPanel, BorderLayout.EAST);
        basicLayer.pack();
        basicLayer.setVisible(true);
    }

    /**
     * Add observers to eventCenter for main layer.
     * */
    private void addObservers() {
        eventCenter.addObserver(SunriseEventEnum.INVOKE_METHOD.getMark(), o -> {
            if (null == o) return;
            SunriseMethodInfo methodInfo = (SunriseMethodInfo) o;
            SunriseMethodUtil.prepareParamForForm(methodInfo, formPanelLayer);
            threadPool.execute(() -> {
                SunriseMethodUtil.runMethod(methodInfo);
                System.out.println(methodInfo.consoleInfoBytes().toString());
                eventCenter.addEvent(
                        new MarkedElement<>(
                                SunriseEventEnum.CONSOLE_SHOW.getMark(methodInfo)
                                , methodInfo));
            });
        });
    }
}
