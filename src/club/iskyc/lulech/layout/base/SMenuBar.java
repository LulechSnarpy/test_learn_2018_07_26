package club.iskyc.lulech.layout.base;

import club.iskyc.lulech.layout.listener.FunctionListener;
import club.iskyc.lulech.layout.util.ClassScannerUtil;
import club.iskyc.lulech.layout.util.MarkedElement;
import club.iskyc.lulech.layout.util.Tree;
import club.iskyc.lulech.layout.util.TreeNode;

import javax.swing.*;
import java.awt.*;

public class SMenuBar extends JMenuBar {
    private static SMenuBar sMenuBar;

    private JLabel jLabel;

    private SMenuBar(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    private void initAll() {
        JMenu jMenu = new JMenu(" Nodes");
        Tree<MarkedElement<String, Class>> tree = ClassScannerUtil.getTreeClassNames();
        TreeNode<MarkedElement<String, Class>> head = tree.getHeadNode();
        addFunctionMenus(head, jMenu);
        this.add(jMenu);
        setOpaque(true);
        setPreferredSize(new Dimension(200, 20));
    }

    private void addFunctionMenus(TreeNode<MarkedElement<String, Class>> node, JMenu jMenu) {
        String title = null;
        for (;;) {
            if (node.getLeaves().size() == 0) {
                if (node.getData().getData() == null) return;
                if (null != title && !title.isEmpty()) {
                    JMenu subMenu = new JMenu(title);
                    jMenu.add(subMenu);
                    jMenu = subMenu;
                }
                JMenuItem item = new JMenuItem(getTitleFormMark(node.getData().getMark()));
                item.addMouseListener(
                        new FunctionListener(jLabel, node.getData().getData()));
                jMenu.add(item);
                return;
            }
            title = null == title || title.isEmpty()?
                    getTitleFormMark(node.getData().getMark())
                    : title + "." + getTitleFormMark(node.getData().getMark());
            if (node.getLeaves().size() > 1) {
                JMenu subMenu = new JMenu(title);
                jMenu.add(subMenu);
                for (TreeNode<MarkedElement<String, Class>> leaf
                        : node.getLeaves()) {
                    addFunctionMenus(leaf, subMenu);
                }
                return;
            }
            node = node.getLeaves().get(0);
        }
    }

    private String getTitleFormMark(String mark) {
        return mark.substring(mark.lastIndexOf(".") + 1);
    }

    public synchronized static JMenuBar getSToolBar(JLabel jLabel) {
        if (null == sMenuBar) {
            sMenuBar = new SMenuBar(jLabel);
            sMenuBar.initAll();
        }
        return sMenuBar;
    }

}
