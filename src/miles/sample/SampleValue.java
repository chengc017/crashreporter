package miles.sample;

public class SampleValue
{

    public SampleValue(double value)
    {
        this.value = Double.valueOf(0.0D);
        setValue(value);
    }

    public SampleValue(long value)
    {
        this.value = Double.valueOf(0.0D);
        setValue(value);
    }

    public Number getValue()
    {
        if(isDouble)
            return asDouble();
        else
            return asLong();
    }

    public Double asDouble()
    {
        return value;
    }

    public Long asLong()
    {
        return Long.valueOf(value.longValue());
    }

    public void setValue(double value)
    {
        this.value = Double.valueOf(value);
        isDouble = true;
    }

    public void setValue(long value)
    {
        this.value = Double.valueOf(value);
        isDouble = false;
    }

    public boolean isDouble()
    {
        return isDouble;
    }

    public void setDouble(boolean aDouble)
    {
        isDouble = aDouble;
    }

    private Double value;
    private boolean isDouble;
}
