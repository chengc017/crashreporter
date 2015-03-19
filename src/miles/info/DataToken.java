package miles.info;

import miles.harvest.type.HarvestableArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class DataToken extends HarvestableArray
{

    public DataToken()
    {
    }

    public DataToken(int accountId, int agentId)
    {
        this.accountId = accountId;
        this.agentId = agentId;
    }

    public JsonArray asJsonArray()
    {
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive(Integer.valueOf(accountId)));
        array.add(new JsonPrimitive(Integer.valueOf(agentId)));
        return array;
    }

    public void clear()
    {
        accountId = 0;
        agentId = 0;
    }

    public int getAccountId()
    {
        return accountId;
    }

    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    public int getAgentId()
    {
        return agentId;
    }

    public void setAgentId(int agentId)
    {
        this.agentId = agentId;
    }

    public boolean isValid()
    {
        return accountId > 0 && agentId > 0;
    }

    public String toString()
    {
        return (new StringBuilder()).append("DataToken{accountId=").append(accountId).append(", agentId=").append(agentId).append('}').toString();
    }

    private int accountId;
    private int agentId;
}
