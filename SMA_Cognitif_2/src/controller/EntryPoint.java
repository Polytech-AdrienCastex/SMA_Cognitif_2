package controller;

import java.util.stream.Collectors;
import model.agent.AgentBuilder;
import model.agent.AgentFactory;
import model.environment.AgentSystem;
import model.message.MailBox;

public class EntryPoint
{
    public static void main(String[] args)
    {
        // Cours : liris.cnrs.fr/~saknine/SMA
        // Mot de passe : akines2015
        
        AgentSystem as = new AgentSystem(
                AgentFactory.createAgentNegociators(new int[]
                {
                    150, 300,
                    250, 500,
                    150, 300
                })
                .map(AgentBuilder::build)
                .collect(Collectors.toList()),
                AgentFactory.createAgentSuppliers(new int[]
                {
                    250, 500,
                    250, 500
                })
                .map(AgentBuilder::build)
                .collect(Collectors.toList()),
                new MailBox());
        
        as.startAgents();
        
        //new MainFrame(as).setVisible(true);
    }
}
