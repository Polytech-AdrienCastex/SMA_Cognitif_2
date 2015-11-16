package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import model.environment.AgentSystem;
import model.environment.Grid;

/**
 *
 * @author Adrien
 */
public class MainFrame extends JFrame implements WindowListener
{
    public MainFrame(AgentSystem as)
    {
        this.as = as;
        Grid grid = as.getGrid();
        
        this.setLayout(new GridLayout(grid.getSize().x, grid.getSize().y, 5, 5));
        
        this.setPreferredSize(new Dimension(400, 400));
        
        grid.getCases()
                .stream()
                .sorted((c1, c2) -> c1.getLocation().y == c2.getLocation().y ? Integer.compare(c1.getLocation().x, c2.getLocation().x) : Integer.compare(c1.getLocation().y, c2.getLocation().y))
                .map(Case::new)
                .forEachOrdered(this::add);
        
        this.addWindowListener(this);
        this.pack();
        
        JFrame mailBoxFrame = new JFrame();
        mailBoxFrame.add(new MailBox(as.getMailBox()));
        mailBoxFrame.setSize(new Dimension(600, 600));
        mailBoxFrame.setLocation(this.getLocation().x + this.getSize().width, this.getLocation().y);
        mailBoxFrame.setVisible(true);
        
        JFrame commandFrame = new JFrame();
        JButton go = new JButton("Go");
        go.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                as.startAgents();
            }
        });
        commandFrame.add(go);
        commandFrame.setLocation(this.getLocation().x + this.getSize().width, this.getLocation().y + mailBoxFrame.getSize().height);
        commandFrame.pack();
        commandFrame.setVisible(true);
    }
    
    private final AgentSystem as;
    
    @Override
    public void windowOpened(WindowEvent e)
    { }

    @Override
    public void windowClosing(WindowEvent e)
    {
        as.stopAgents();
    }

    @Override
    public void windowClosed(WindowEvent e)
    { }

    @Override
    public void windowIconified(WindowEvent e)
    { }

    @Override
    public void windowDeiconified(WindowEvent e)
    { }

    @Override
    public void windowActivated(WindowEvent e)
    { }

    @Override
    public void windowDeactivated(WindowEvent e)
    { }
}
