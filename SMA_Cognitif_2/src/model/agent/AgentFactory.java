package model.agent;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AgentFactory
{
    private AgentFactory()
    { }
    
    protected static Stream<AgentBuilder> createAgents(int[] values, Supplier<AgentBuilder> supplier)
    {
        return IntStream.range(0, (int)(values.length / 2))
                .map(i -> i*2)
                .mapToObj(i -> supplier.get()
                        .setPriceObjective(values[i])
                        .setRange(values[i + 1] - values[i])
                        .setInitialOffer(values[i]))
                .peek(b -> b.setVerbose(true))
                .peek(b -> b.setMaxNbTry(20))
                .peek(b -> b.setMinNbTry(10))
                .peek(b -> b.setSlope(0.2));
    }
    
    public static Stream<AgentNegociator.Builder> createAgentNegociators(int[] values)
    {
        return createAgents(values, () -> AgentNegociator.create())
                .map(AgentNegociator.Builder.class::cast);
    }
    public static Stream<AgentSupplier.Builder> createAgentSuppliers(int[] values)
    {
        return createAgents(values, () -> AgentSupplier.create())
                .map(AgentSupplier.Builder.class::cast);
    }
}
