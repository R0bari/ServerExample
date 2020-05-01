package org.example.model;

import java.util.List;

public class Answer
{
    private String status;
    private List<Item> items;

    public Answer(String status, List<Item> items)
    {
        this.status = status;
        this.items = items;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }
}
