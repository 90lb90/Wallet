import java.io.IOException;
import java.math.BigDecimal;

public interface IfaceBalance {

    public void parseDataFile() throws IOException;
    public BigDecimal currentValue();

}
