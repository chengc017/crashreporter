package miles.sample;

import miles.harvest.type.HarvestableArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class Sample extends HarvestableArray
{
	public  static enum SampleType {
		MEMORY,CPU
	}
	
	
    public Sample(SampleType type)
    {
        setSampleType(type);
        setTimestamp(System.currentTimeMillis());
    }

    public Sample(long timestamp)
    {
        setTimestamp(timestamp);
    }

    public Sample(long timestamp, SampleValue sampleValue)
    {
        setTimestamp(timestamp);
        setSampleValue(sampleValue);
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public SampleValue getSampleValue()
    {
        return sampleValue;
    }

    public void setSampleValue(SampleValue sampleValue)
    {
        this.sampleValue = sampleValue;
    }

    public void setSampleValue(double value)
    {
        sampleValue = new SampleValue(value);
    }

    public void setSampleValue(long value)
    {
        sampleValue = new SampleValue(value);
    }

    public Number getValue()
    {
        return sampleValue.getValue();
    }

    public SampleType getSampleType()
    {
        return type;
    }

    public void setSampleType(SampleType type)
    {
        this.type = type;
    }

    public JsonArray asJsonArray()
    {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(Long.valueOf(timestamp)));
        jsonArray.add(new JsonPrimitive(sampleValue.getValue()));
        return jsonArray;
    }

    private long timestamp;
    private SampleValue sampleValue;
    private SampleType type;
}